package com.matbadev.dabirva.example.util

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import java.util.*

class UnicastObservableQueue<E> {

    private val internalBuffer: MutableList<E> = mutableListOf()

    val buffer: List<E>
        get() = Collections.unmodifiableList(internalBuffer)

    private var currentObserver: ((E) -> Unit)? = null

    @AnyThread
    fun add(element: E) {
        Threads.runOnMain {
            currentObserver?.invoke(element) ?: internalBuffer.add(element)
        }
    }

    @MainThread
    fun observe(observer: (E) -> Unit) {
        if (!Threads.isMainThread()) throw UnsupportedOperationException()
        if (currentObserver != null) throw IllegalStateException()
        currentObserver = observer
        while (internalBuffer.isNotEmpty()) {
            observer(internalBuffer.removeFirst())
        }
    }

    fun stopObserving() {
        currentObserver = null
    }

}
