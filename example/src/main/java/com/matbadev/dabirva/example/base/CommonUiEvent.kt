package com.matbadev.dabirva.example.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlin.reflect.KClass

typealias StringProvider = (Context) -> String

sealed class CommonUiEvent

data class StartActivityEvent(
    val activityClass: Class<out Activity>,
    val options: Bundle? = null,
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
