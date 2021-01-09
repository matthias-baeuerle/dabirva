package com.matbadev.dabirva.example.ui.launch

import com.matbadev.dabirva.LinearOrientation
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.StartActivityEvent
import com.matbadev.dabirva.example.base.StartAppActivityEvent
import com.matbadev.dabirva.example.ui.simple.SimpleListActivity
import com.matbadev.dabirva.example.ui.stickyheader.StickyHeaderListActivity
import com.matbadev.dabirva.example.ui.stickyheader.StickyHeaderListArguments

class LaunchActivityViewModel : BaseScreenViewModel<LaunchActivityEvent, LaunchActivityArguments>() {

    fun startSimpleListActivity() {
        queueCommonUiEvent(
            StartActivityEvent(
                activityClass = SimpleListActivity::class,
            )
        )
    }

    fun startHorizontalStickyHeaderListActivity() {
        startStickyHeaderListActivity(LinearOrientation.HORIZONTAL)
    }

    fun startVerticalStickyHeaderListActivity() {
        startStickyHeaderListActivity(LinearOrientation.VERTICAL)
    }

    private fun startStickyHeaderListActivity(listOrientation: LinearOrientation) {
        queueCommonUiEvent(
            StartAppActivityEvent(
                activityClass = StickyHeaderListActivity::class,
                arguments = StickyHeaderListArguments(
                    listOrientation = listOrientation,
                ),
            )
        )
    }

}
