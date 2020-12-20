package com.matbadev.dabirva

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.matbadev.dabirva.TestRecyclables.A
import com.matbadev.dabirva.TestRecyclables.B
import com.matbadev.dabirva.TestRecyclables.C
import com.matbadev.dabirva.TestRecyclables.D
import com.matbadev.dabirva.TestRecyclables.E
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class DabirvaRecyclablesDiffingInstrumentedTest {

    @Rule
    @JvmField
    var mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Mock
    private lateinit var adapterDataObserver: AdapterDataObserver

    @Test
    fun insertSingle() {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, B, D, C),
        )
        verify(adapterDataObserver).onItemRangeInserted(2, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun insertMultiple() {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, B, D, C, E),
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeInserted(3, 1)
            verify(adapterDataObserver).onItemRangeInserted(2, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeSingle() {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, C),
        )
        verify(adapterDataObserver).onItemRangeRemoved(1, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeMultiple() {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(B),
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeRemoved(2, 1)
            verify(adapterDataObserver).onItemRangeRemoved(0, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeSingle() {
        runTest(
            initialRecyclables = listOf(A, TestRecyclable(2, "initial"), C),
            updatedRecyclables = listOf(A, TestRecyclable(2, "updated"), C),
        )
        verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeMultiple() {
        runTest(
            initialRecyclables = listOf(A, TestRecyclable(2, "initial"), C, TestRecyclable(4, "initial")),
            updatedRecyclables = listOf(A, TestRecyclable(2, "updated"), C, TestRecyclable(4, "updated")),
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeChanged(3, 1, null)
            verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveSingle() {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(B, A, C),
        )
        verify(adapterDataObserver).onItemRangeMoved(1, 0, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveMultiple() {
        runTest(
            initialRecyclables = listOf(A, B, C, D),
            updatedRecyclables = listOf(D, A, C, B),
        )
        inOrder(adapterDataObserver).apply {
            // A  B  C  D
            //    |<<|     (first call)
            // A  C  B  D
            // |<<<<<<<<|  (second call)
            // D  A  C  B
            verify(adapterDataObserver).onItemRangeMoved(2, 1, 1)
            verify(adapterDataObserver).onItemRangeMoved(3, 0, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    private fun runTest(initialRecyclables: List<Recyclable>, updatedRecyclables: List<Recyclable>) {
        val recyclerView = RecyclerView(context)
        val initialRecyclerData = RecyclerData(
            recyclables = initialRecyclables,
        )
        val adapter = Dabirva(initialRecyclerData)
        adapter.registerAdapterDataObserver(adapterDataObserver)
        adapter.attachRecyclerView(recyclerView)
        adapter.recyclerData = initialRecyclerData.copy(
            recyclables = updatedRecyclables,
        )
    }

}
