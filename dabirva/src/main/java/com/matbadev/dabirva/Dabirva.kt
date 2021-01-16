package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
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
            val oldData: DabirvaData = field
            field = newData
            if (oldData != newData) {
                onDataChanged(oldData, newData)
            }
        }

    private val decorationUpdater = RecyclerViewDecorationUpdater()

    private var itemsDiffer: ConfigAsyncListDiffer<Diffable>? = null

    private var attachedRecyclerView: RecyclerView? = null

    init {
        setHasStableIds(true)
        refreshItemsDiffer(initialData.diffExecutor)
    }

    private fun onDataChanged(oldData: DabirvaData, newData: DabirvaData) {
        refreshItemsDiffer(newData.diffExecutor)
        refreshItemsInAdapter(oldData.recyclables, newData.recyclables)
        attachedRecyclerView?.let { recyclerView: RecyclerView ->
            refreshDecorationsInRecyclerView(recyclerView, newData.decorations)
        }
    }

    override fun getItemCount(): Int {
        return data.recyclables.size
    }

    override fun getItemId(position: Int): Long {
        val recyclable: Recyclable = data.recyclables[position]
        return recyclable.id
    }

    override fun getItemViewType(position: Int): Int {
        val recyclable: Recyclable = data.recyclables[position]
        return recyclable.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val recyclable: Recyclable = data.recyclables[position]
        holder.bindItem(recyclable)
    }

    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachRecyclerView(recyclerView)
        refreshDecorationsInRecyclerView(recyclerView, data.decorations)
    }

    @VisibleForTesting
    fun attachRecyclerView(recyclerView: RecyclerView) {
        attachedRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        detachRecyclerView()
    }

    @VisibleForTesting
    fun detachRecyclerView() {
        attachedRecyclerView = null
    }

    private fun refreshItemsDiffer(diffExecutor: Executor?) {
        if (diffExecutor == null) {
            itemsDiffer = null
        } else {
            val currentDiffConfig: AsyncDifferConfig<Diffable>? = itemsDiffer?.config
            if (currentDiffConfig?.backgroundThreadExecutor != diffExecutor) {
                itemsDiffer = ConfigAsyncListDiffer(
                    AdapterListUpdateCallback(this),
                    AsyncDifferConfig.Builder(DiffableDiffUtilItemCallback())
                        .setBackgroundThreadExecutor(diffExecutor)
                        .build(),
                )
            }
        }
    }

    private fun refreshItemsInAdapter(oldItems: List<Recyclable>, newItems: List<Recyclable>) {
        val differ: AsyncListDiffer<Diffable>? = itemsDiffer
        if (differ != null) {
            differ.submitList(newItems)
        } else {
            refreshItemsInAdapterSync(oldItems, newItems)
        }
    }

    private fun refreshItemsInAdapterSync(oldItems: List<Recyclable>, newItems: List<Recyclable>) {
        val diffCallback: DiffUtil.Callback = DiffableDiffUtilCallback(oldItems, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun refreshDecorationsInRecyclerView(
        recyclerView: RecyclerView,
        newDecorations: List<RecyclerView.ItemDecoration>,
    ) {
        decorationUpdater.updateDecorations(recyclerView, newDecorations)
    }

}
