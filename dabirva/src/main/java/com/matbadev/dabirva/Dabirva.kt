package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
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
import java.util.Objects
import java.util.concurrent.Executor

open class Dabirva : RecyclerView.Adapter<DataBindingViewHolder>() {

    var items: List<ItemViewModel> = listOf()
        set(newItems) {
            val oldItems = field
            field = newItems
            refreshItemsInAdapter(oldItems, newItems)
        }

    var diffExecutor: Executor? = null
        set(newDiffExecutor) {
            val oldDiffExecutor = field
            field = newDiffExecutor
            refreshItemsDiffer(oldDiffExecutor, newDiffExecutor)
        }

    private var itemsDiffer: ConfigAsyncListDiffer<Diffable>? = null

    private var attachedRecyclerView: RecyclerView? = null

    init { // Stable IDs are not required when using DiffUtil.
        // See: https://stackoverflow.com/a/62281250/
        setHasStableIds(false)
    }

    final override fun getItemCount(): Int {
        return items.size
    }

    final override fun getItemViewType(position: Int): Int {
        val item: ItemViewModel = items[position]
        return item.layoutId
    }

    final override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    final override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    @CallSuper
    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item: ItemViewModel = items[position]
        holder.bindViewModel(item)
    }

    final override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    @CallSuper
    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachRecyclerView(recyclerView)
    }

    @VisibleForTesting
    internal fun attachRecyclerView(recyclerView: RecyclerView) {
        attachedRecyclerView = recyclerView
    }

    @CallSuper
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Dabirva
        return items == other.items && diffExecutor == other.diffExecutor
    }

    override fun hashCode(): Int {
        return Objects.hash(items, diffExecutor)
    }

    override fun toString(): String {
        return "Dabirva(items=$items, diffExecutor=$diffExecutor)"
    }

}
