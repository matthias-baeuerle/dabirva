package com.matbadev.dabirva.example.ui.launch

import android.app.Activity
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.StartActivityEvent
import com.matbadev.dabirva.example.ui.click.ClickListActivity
import com.matbadev.dabirva.example.ui.simple.SimpleListActivity
import com.matbadev.dabirva.example.ui.stickyheader.horizontal.HorizontalStickyHeaderListActivity
import com.matbadev.dabirva.example.ui.stickyheader.vertical.VerticalStickyHeaderListActivity
import kotlin.reflect.KClass

class LaunchActivityViewModel : BaseScreenViewModel<Parcelable, LaunchActivityEvent>() {

    fun startSimpleListActivity() {
        startActivity(SimpleListActivity::class)
    }

    fun startClickListActivity() {
        startActivity(ClickListActivity::class)
    }

    fun startHorizontalStickyHeaderListActivity() {
        startActivity(HorizontalStickyHeaderListActivity::class)
    }

    fun startVerticalStickyHeaderListActivity() {
        startActivity(VerticalStickyHeaderListActivity::class)
    }

    private fun startActivity(activityClass: KClass<out Activity>) {
        queueCommonUiEvent(
            StartActivityEvent(activityClass),
        )
    }

}
