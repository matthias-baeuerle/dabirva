package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.useActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BasicInstrumentedTest : BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
    activityClass = TestActivity::class,
) {

    @Test
    fun defaultDabirva() {
        val recyclerView: RecyclerView = scenario.useActivity { it.findViewById(R.id.recycler_view) }
        val adapter: RecyclerView.Adapter<*>? = recyclerView.adapter

        assertTrue(adapter is Dabirva)
        adapter as Dabirva
        assertFalse(adapter.hasStableIds())
        assertNull(adapter.diffExecutor)
        assertTrue(adapter.items.isEmpty())
    }

}
