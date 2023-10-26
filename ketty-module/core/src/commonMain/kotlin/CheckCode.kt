package io.ketty.module.core

/**
 * Various check codes returned from the [ItemVisitor.check] routine
 */
enum class CheckCode {
    /**
     * Used if the check fails to trigger the vulnerability, or even detect the service.
     */
    SAFE,

    /**
     * The target is running the service in question, but the check fails to determine whether the target is vulnerable or not.
     */
    DETECTED,

    /**
     * This is used if the vulnerability is determined based on passive reconnaissance. For example, version, banner grabbing, or simply having the resource that’s known to be vulnerable.
     */
    APPEARS,

    /**
     * Only used if the check is able to actually take advantage of the bug, and obtain some sort of hard evidence. For example, for a command execution type bug, get a command output from the target system. For a directory traversal, read a file from the target, etc. Since this level of check is pretty aggressive in nature, you should not try to DoS the host as a way to prove the vulnerability.
     */
    VULNERABLE,
}
