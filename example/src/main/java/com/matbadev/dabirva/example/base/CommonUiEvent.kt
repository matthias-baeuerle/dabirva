package com.matbadev.dabirva.example.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import kotlin.reflect.KClass

typealias StringProvider = (Context) -> String

sealed class CommonUiEvent

data class StartActivityEvent(
    val activityClass: KClass<out Activity>,
    val options: Bundle? = null,
) : CommonUiEvent()

data class StartAppActivityEvent<A : BaseScreenArguments>(
    val activityClass: KClass<out BaseActivity<*, A, *>>,
    val options: Bundle? = null,
    val arguments: A? = null,
) : CommonUiEvent()

data class ShowToastEvent(
    val messageProvider: StringProvider,
    val duration: ToastDuration = ToastDuration.SHORT,
) : CommonUiEvent()

enum class ToastDuration(
    val androidValue: Int,
) {
    SHORT(Toast.LENGTH_SHORT),
    LONG(Toast.LENGTH_LONG),
}
