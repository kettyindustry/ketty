package io.ketty.core

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class Ketty(
    private val module: Module, private val concurrent: Int = 1, parentContext: CoroutineContext = EmptyCoroutineContext
) : KettySpecifications,
    CoroutineScope by CoroutineScope(parentContext + Job() + CoroutineName("ModuleKetty($module, $concurrent)")) {
    private val semaphore = Semaphore(concurrent)

    private val itemsReceived = MutableStateFlow(0L)
    private val itemsProcessed = MutableStateFlow(0L)

    private val _itemChannel = Channel<Item>(Channel.RENDEZVOUS)
    private val _checkCodeChannel = Channel<Pair<Item, CheckCode>>(Channel.RENDEZVOUS)
    private val _checkErrorChannel = Channel<Pair<Item, Throwable>>(Channel.RENDEZVOUS)

    override val items: SendChannel<Item> = _itemChannel
    override val checkCodes: ReceiveChannel<Pair<Item, CheckCode>> = _checkCodeChannel
    override val checkErrors: ReceiveChannel<Pair<Item, Throwable>> = _checkErrorChannel

    private val worker = launch(CoroutineName("ketty item dispatcher")) {
        for (item in _itemChannel) {
            itemsReceived.update { it + 1 }
            semaphore.acquire()

            launch {
                try {
                    val checkCode = module.check(item)
                    _checkCodeChannel.send(item to checkCode)
                } catch (exception: Throwable) {
                    _checkErrorChannel.send(item to exception)
                } finally {
                    semaphore.release()
                    itemsProcessed.update { it + 1 }
                }
            }
        }
    }

    override suspend fun join() {
        while (true) {
            val received = itemsReceived.value
            val processed = itemsProcessed.value
            check(received >= processed) { "Processed more items than expected" }

            if (received == processed) {
                break
            }

            yield()
        }
    }

    override suspend fun close() {
        worker.cancelAndJoin()
        _itemChannel.close()
        _checkCodeChannel.close()
        _checkErrorChannel.close()
    }
}
