package com.matbadev.dabirva.example.base

import android.os.Bundle
import androidx.annotation.AnyThread
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.matbadev.dabirva.example.util.UnicastObservableQueue
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseScreenViewModel<SE> : ViewModel() {

    companion object {
        private const val VIEW_MODEL_STATE_KEY = "viewModel"
    }

    private val commonUiEvents = UnicastObservableQueue<CommonUiEvent>()

    private val screenUiEvents = UnicastObservableQueue<SE>()

    private val firstCreationDone = AtomicBoolean(false)

    fun registerUi(arguments: Bundle?, savedInstanceState: Bundle?, uiEventHandler: UiEventHandler<SE>) {
        if (firstCreationDone.compareAndSet(false, true)) {
            Timber.d("Initializing ViewModel $this with arguments $arguments and savedInstanceState $savedInstanceState")
            initWithArguments(arguments ?: Bundle.EMPTY)
            if (savedInstanceState != null) {
                // App process was killed by OS
                val viewModelState: Bundle? = savedInstanceState.getBundle(VIEW_MODEL_STATE_KEY)
                onRestoreViewModelState(viewModelState ?: Bundle.EMPTY)
            }
        } else {
            Timber.d("View model $this was already initialized")
        }
        commonUiEvents.observe(uiEventHandler::handleCommonUiEvent)
        screenUiEvents.observe(uiEventHandler::handleScreenUiEvent)
    }

    fun unregisterUi() {
        commonUiEvents.stopObserving()
        screenUiEvents.stopObserving()
    }

    open fun initWithArguments(arguments: Bundle) {
    }

    @AnyThread
    fun queueCommonUiEvent(event: CommonUiEvent) {
        commonUiEvents.add(event)
    }

    @AnyThread
    fun queueScreenUiEvent(event: SE) {
        screenUiEvents.add(event)
    }

    fun saveViewModelState(targetBundle: Bundle) {
        val viewModelState = Bundle()
        onSaveViewModelState(viewModelState)
        targetBundle.putBundle(VIEW_MODEL_STATE_KEY, viewModelState)
    }

    open fun onSaveViewModelState(viewModelState: Bundle) {
    }

    open fun onRestoreViewModelState(viewModelState: Bundle) {
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Timber.d("Clearing viewModel: $this")
        checkEventBuffersEmpty()
    }

    private fun checkEventBuffersEmpty() {
        val commonEventsBuffer = commonUiEvents.buffer
        if (commonEventsBuffer.isNotEmpty()) {
            Timber.w("Throwing away ${commonEventsBuffer.size} common UI events")
        }
        val screenEventsBuffer = screenUiEvents.buffer
        if (screenEventsBuffer.isNotEmpty()) {
            Timber.w("Throwing away ${screenEventsBuffer.size} screen UI events")
        }
    }

}
