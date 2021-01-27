package com.matbadev.dabirva

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

class TrampolineExecutor : Executor {

    private val executedCommandsCountRef = AtomicInteger(0)

    val executedCommandsCount
        get() = executedCommandsCountRef.get()

    override fun execute(command: Runnable) {
        command.run()
        executedCommandsCountRef.incrementAndGet()
    }

}
