package com.matbadev.dabirva.example

import android.app.Application
import android.content.Context
import android.util.Log
import timber.log.Timber

class App : Application() {

    companion object {
        fun of(context: Context): App {
            return context.applicationContext as App
        }
    }

    lateinit var repositories: AppRepositories

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) LineDebugTree() else ReleaseTree())
        repositories = AppRepositories()
    }

    private class LineDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return super.createStackElementTag(element) + ":" + element.lineNumber
        }
    }

    private class ReleaseTree : Timber.DebugTree() {
        override fun isLoggable(tag: String?, priority: Int): Boolean {
            return priority >= Log.INFO
        }
    }

}
