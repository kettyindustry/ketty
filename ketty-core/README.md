# ![](../docs/cpu.png) ketty

`ketty-core` contains the mass scanning logic.
It manages the parallel processing and handling of module errors.

## Usage

As soon as a item is emitted, ketty will immediately start.

```kotlin
val items: Flow<Item> = ...
val ketty = Ketty(concurrent = 100)

// Send all items to ketty
launch {
    ketty.itemSharedFlow.emitAll(items)
}

// Receive all check results from ketty
launch {
    ketty.codeSharedFlow.collect { (item, code) ->
        println("$item : $code")
    }
}

ketty.join() // Wait for ketty to process all remaining items
ketty.close() // Release all resources used by ketty
```
