package com.matbadev.dabirva.example.util

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

/**
 * [Executor] which runs a command in the same thread that queued it.
 */
class TrampolineExecutor : Executor {

    private val executedCommandsCountRef = AtomicInteger(0)

    /**
     * The number of commands which has been executed successfully so far.
     */
    val executedCommandsCount
        get() = executedCommandsCountRef.get()

    override fun execute(command: Runnable) {
        try {
            command.run()
        } finally {
            executedCommandsCountRef.incrementAndGet()
        }
    }

}
