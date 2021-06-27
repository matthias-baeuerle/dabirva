package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Instantiates or updates [Dabirva] with [dabirvaData] and sets it as adapter of [recyclerView].
 *
 * This method should never be called directly but only with the provided [BindingAdapter], e.g.:
 *
 * ```xml
 * <androidx.recyclerview.widget.RecyclerView
 *   android:id="@+id/simple_list_recycler"
 *   android:layout_width="match_parent"
 *   android:layout_height="match_parent"
 *   android:orientation="vertical"
 *   app:dabirvaData="@{viewModel.dabirvaData}"
 *   app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
 *   tools:listitem="@layout/item_note" />
 * ```
 *
 * @throws IllegalStateException when [recyclerView] already has an adapter assigned
 * that is not an instance of [Dabirva].
 */
@BindingAdapter("dabirvaData")
@Suppress("CascadeIf")
fun setDabirvaData(recyclerView: RecyclerView, dabirvaData: DabirvaData) {
    val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
    if (currentAdapter == null) {
        recyclerView.adapter = Dabirva(dabirvaData)
    } else if (currentAdapter is Dabirva) {
        currentAdapter.data = dabirvaData
    } else {
        throw IllegalStateException("Different adapter already set")
    }
}
