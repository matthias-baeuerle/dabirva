package com.matbadev.dabirva.example.ui.launch

import android.app.Activity
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.StartActivityEvent
import com.matbadev.dabirva.example.ui.simple.SimpleListActivity
import com.matbadev.dabirva.example.ui.stickyheader.horizontal.HorizontalStickyHeaderListActivity
import com.matbadev.dabirva.example.ui.stickyheader.vertical.VerticalStickyHeaderListActivity
import kotlin.reflect.KClass

class LaunchActivityViewModel : BaseScreenViewModel<LaunchActivityEvent, LaunchActivityArguments>() {

    fun startSimpleListActivity() {
        startActivity(SimpleListActivity::class)
    }

    fun startHorizontalStickyHeaderListActivity() {
        startActivity(HorizontalStickyHeaderListActivity::class)
    }

    fun startVerticalStickyHeaderListActivity() {
        startActivity(VerticalStickyHeaderListActivity::class)
    }

    private fun startActivity(activityClass: KClass<out Activity>) {
        queueCommonUiEvent(
            StartActivityEvent(activityClass)
        )
    }

}
