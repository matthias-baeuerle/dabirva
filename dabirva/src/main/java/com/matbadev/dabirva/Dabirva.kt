package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.internal.ConfigAsyncListDiffer
import com.matbadev.dabirva.internal.DiffableDiffUtilCallback
import com.matbadev.dabirva.internal.DiffableDiffUtilItemCallback
import com.matbadev.dabirva.internal.RecyclerViewDecorationUpdater
import java.util.concurrent.Executor

class Dabirva(
    initialData: DabirvaData,
) : RecyclerView.Adapter<DataBindingViewHolder>() {

    var data: DabirvaData = initialData
        set(newData) {
            val oldData = field
            field = newData
            onDataChanged(oldData, newData)
        }

    private var itemsDiffer: ConfigAsyncListDiffer<Diffable>? = null

    private var attachedRecyclerView: RecyclerView? = null

    init { // Stable IDs are not required when using DiffUtil.
        // See: https://stackoverflow.com/a/62281250/
        setHasStableIds(false)
        refreshItemsDiffer(null, initialData.diffExecutor)
    }

    private fun onDataChanged(oldData: DabirvaData, newData: DabirvaData) {
        refreshItemsDiffer(oldData.diffExecutor, newData.diffExecutor)
        refreshItemsInAdapter(oldData.items, newData.items)
        attachedRecyclerView?.let { recyclerView: RecyclerView ->
            refreshDecorationsInRecyclerView(recyclerView, newData.itemDecorations)
        }
    }

    override fun getItemCount(): Int {
        return data.items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item: ItemViewModel = data.items[position]
        return item.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item: ItemViewModel = data.items[position]
        holder.bindViewModel(item)
    }

    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachRecyclerView(recyclerView)
        refreshDecorationsInRecyclerView(recyclerView, data.itemDecorations)
    }

    @VisibleForTesting
    internal fun attachRecyclerView(recyclerView: RecyclerView) {
        attachedRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        detachRecyclerView()
    }

    @VisibleForTesting
    internal fun detachRecyclerView() {
        attachedRecyclerView = null
    }

    private fun refreshItemsDiffer(oldDiffExecutor: Executor?, newDiffExecutor: Executor?) {
        if (oldDiffExecutor != newDiffExecutor) {
            itemsDiffer = if (newDiffExecutor != null) {
                ConfigAsyncListDiffer(
                    AdapterListUpdateCallback(this),
                    DiffableDiffUtilItemCallback(),
                    newDiffExecutor,
                )
            } else {
                null
            }
        }
    }

    private fun refreshItemsInAdapter(oldItems: List<ItemViewModel>, newItems: List<ItemViewModel>) {
        val differ: AsyncListDiffer<Diffable>? = itemsDiffer
        if (differ != null) {
            differ.submitList(newItems)
        } else {
            refreshItemsInAdapterSync(oldItems, newItems)
        }
    }

    private fun refreshItemsInAdapterSync(oldItems: List<ItemViewModel>, newItems: List<ItemViewModel>) {
        val diffCallback: DiffUtil.Callback = DiffableDiffUtilCallback(oldItems, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun refreshDecorationsInRecyclerView(
        recyclerView: RecyclerView,
        newDecorations: List<RecyclerView.ItemDecoration>,
    ) {
        RecyclerViewDecorationUpdater.updateDecorations(recyclerView, newDecorations)
    }

    override fun toString(): String {
        return "Dabirva(data=$data)"
    }

}
