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
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DabirvaRecyclablesDiffingInstrumentedTest {

    enum class DiffExecutorMode {
        SYNC,
        ASYNC,
    }

    @Rule
    @JvmField
    var mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Mock
    private lateinit var adapterDataObserver: AdapterDataObserver

    @Test
    fun insertSingleSync() {
        insertSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun insertSingleAsync() {
        insertSingle(DiffExecutorMode.ASYNC)
    }

    private fun insertSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, B, D, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeInserted(2, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun insertMultipleSync() {
        insertMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun insertMultipleAsync() {
        insertMultiple(DiffExecutorMode.ASYNC)
    }

    private fun insertMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, B, D, C, E),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeInserted(3, 1)
            verify(adapterDataObserver).onItemRangeInserted(2, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeSingleSync() {
        removeSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun removeSingleAsync() {
        removeSingle(DiffExecutorMode.ASYNC)
    }

    private fun removeSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(A, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeRemoved(1, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeMultipleSync() {
        removeMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun removeMultipleAsync() {
        removeMultiple(DiffExecutorMode.ASYNC)
    }

    private fun removeMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(B),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeRemoved(2, 1)
            verify(adapterDataObserver).onItemRangeRemoved(0, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeSingleSync() {
        changeSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun changeSingleAsync() {
        changeSingle(DiffExecutorMode.ASYNC)
    }

    private fun changeSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, TestRecyclable(2, "initial"), C),
            updatedRecyclables = listOf(A, TestRecyclable(2, "updated"), C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeMultipleSync() {
        changeMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun changeMultipleAsync() {
        changeMultiple(DiffExecutorMode.ASYNC)
    }

    private fun changeMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, TestRecyclable(2, "initial"), C, TestRecyclable(4, "initial")),
            updatedRecyclables = listOf(A, TestRecyclable(2, "updated"), C, TestRecyclable(4, "updated")),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeChanged(3, 1, null)
            verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveSingleSync() {
        moveSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun moveSingleAsync() {
        moveSingle(DiffExecutorMode.ASYNC)
    }

    private fun moveSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C),
            updatedRecyclables = listOf(B, A, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeMoved(1, 0, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveMultipleSync() {
        moveMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun moveMultipleAsync() {
        moveMultiple(DiffExecutorMode.ASYNC)
    }

    private fun moveMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialRecyclables = listOf(A, B, C, D),
            updatedRecyclables = listOf(D, A, C, B),
            diffExecutorMode = diffExecutorMode,
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

    private fun runTest(
        initialRecyclables: List<Recyclable>,
        updatedRecyclables: List<Recyclable>,
        diffExecutorMode: DiffExecutorMode,
    ) = when (diffExecutorMode) {
        DiffExecutorMode.SYNC -> runTestSync(initialRecyclables, updatedRecyclables)
        DiffExecutorMode.ASYNC -> runTestAsync(initialRecyclables, updatedRecyclables)
    }

    private fun runTestSync(initialRecyclables: List<Recyclable>, updatedRecyclables: List<Recyclable>) {
        val recyclerView = RecyclerView(context)
        val adapter = Dabirva(
            initialRecyclerData = RecyclerData(
                recyclables = initialRecyclables,
            ),
        )
        adapter.registerAdapterDataObserver(adapterDataObserver)
        adapter.attachRecyclerView(recyclerView)
        adapter.recyclerData = adapter.recyclerData.copy(
            recyclables = updatedRecyclables,
        )
    }

    private fun runTestAsync(initialRecyclables: List<Recyclable>, updatedRecyclables: List<Recyclable>) {
        // Create blocked diff executor
        val diffExecutorBlocker = CountDownLatch(1)
        val diffExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        diffExecutor.submit {
            diffExecutorBlocker.await()
        }

        val recyclerView = RecyclerView(context)
        val adapter = Dabirva(
            initialRecyclerData = RecyclerData(
                diffExecutor = diffExecutor,
            ),
        )

        // The initial insert happens synchronously (see AsyncListDiffer)
        adapter.recyclerData = adapter.recyclerData.copy(
            recyclables = initialRecyclables,
        )

        adapter.registerAdapterDataObserver(adapterDataObserver)
        adapter.attachRecyclerView(recyclerView)
        adapter.recyclerData = adapter.recyclerData.copy(
            recyclables = updatedRecyclables,
        )

        verifyNoInteractions(adapterDataObserver)

        // Unblock diff executor which starts async diffing
        diffExecutorBlocker.countDown()

        // Wait for async diffing to start
        Thread.sleep(10)

        // Wait for async diffing to complete
        diffExecutor.submit { }.get(10, TimeUnit.SECONDS)
    }

}
