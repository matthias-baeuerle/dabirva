package com.matbadev.dabirva.example.ui.launch

import android.app.Activity
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.StartActivityEvent
import com.matbadev.dabirva.example.ui.simple.SimpleListActivity
import com.matbadev.dabirva.example.ui.stickyheader.StickyHeaderListActivity
import kotlin.reflect.KClass

class LaunchActivityViewModel : BaseScreenViewModel<LaunchActivityEvent>() {

    fun startSimpleListActivity() {
        startActivity(SimpleListActivity::class)
    }

    fun startStickyHeaderListActivity() {
        startActivity(StickyHeaderListActivity::class)
    }

    private fun startActivity(activityClass: KClass<out Activity>) {
        queueCommonUiEvent(
            StartActivityEvent(
                activityClass = activityClass.java,
            )
        )
    }

}
