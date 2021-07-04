package com.matbadev.dabirva.example.ui.launch

import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.example.base.StartAppActivityEvent
import com.matbadev.dabirva.example.data.NoteGenerator
import com.matbadev.dabirva.example.ui.click.ClickListActivity
import com.matbadev.dabirva.example.ui.item.ItemActivity
import com.matbadev.dabirva.example.ui.item.ItemActivityArguments
import com.matbadev.dabirva.example.ui.stickyheader.horizontal.HorizontalStickyHeaderListActivity
import com.matbadev.dabirva.example.ui.stickyheader.vertical.VerticalStickyHeaderListActivity

class LaunchActivityViewModel : BaseScreenViewModel<Parcelable, LaunchActivityEvent>() {

    fun startSimpleListActivity() {
        queueCommonUiEvent(StartAppActivityEvent(
            activityClass = ItemActivity::class,
            arguments = ItemActivityArguments(
                items = NoteGenerator.generateNotes(),
            ),
        ))
    }

    fun startClickListActivity() {
        queueCommonUiEvent(StartAppActivityEvent(
            activityClass = ClickListActivity::class,
        ))
    }

    fun startHorizontalStickyHeaderListActivity() {
        queueCommonUiEvent(StartAppActivityEvent(
            activityClass = HorizontalStickyHeaderListActivity::class,
        ))
    }

    fun startVerticalStickyHeaderListActivity() {
        queueCommonUiEvent(StartAppActivityEvent(
            activityClass = VerticalStickyHeaderListActivity::class,
        ))
    }

}
