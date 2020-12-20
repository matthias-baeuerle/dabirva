package com.matbadev.dabirva.example.util

import android.os.Handler
import android.os.Looper

object Threads {

    private val mainHandler = Handler(Looper.getMainLooper())

    fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }

    fun runOnMain(runnable: Runnable) {
        if (isMainThread()) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

}
