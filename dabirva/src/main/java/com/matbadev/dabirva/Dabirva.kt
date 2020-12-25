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
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.matbadev.dabirva.internal.ConfigAsyncListDiffer
import com.matbadev.dabirva.internal.IdentifiablesDiffUtilCallback
import com.matbadev.dabirva.internal.IdentifiablesDiffUtilItemCallback
import com.matbadev.dabirva.internal.RecyclerViewDecorationUpdater
import java.util.concurrent.Executor

class Dabirva(
    initialRecyclerData: RecyclerData,
) : RecyclerView.Adapter<DataBindingViewHolder>() {

    var recyclerData: RecyclerData = initialRecyclerData
        set(newData) {
            val oldData: RecyclerData = field
            field = newData
            if (oldData != newData) {
                onRecyclerDataChanged(oldData, newData)
            }
        }

    private val decorationUpdater = RecyclerViewDecorationUpdater()

    private var itemsDiffer: ConfigAsyncListDiffer<Identifiable>? = null

    private var attachedRecyclerView: RecyclerView? = null

    init {
        setHasStableIds(true)
        refreshItemsDiffer(initialRecyclerData.diffExecutor)
    }

    private fun onRecyclerDataChanged(oldData: RecyclerData, newData: RecyclerData) {
        refreshItemsDiffer(newData.diffExecutor)
        refreshItemsInAdapter(oldData.recyclables, newData.recyclables)
        attachedRecyclerView?.let { recyclerView: RecyclerView ->
            refreshDecorationsInRecyclerView(recyclerView, newData.decorations)
        }
    }

    override fun getItemCount(): Int {
        return recyclerData.recyclables.size
    }

    override fun getItemId(position: Int): Long {
        val recyclable: Recyclable = recyclerData.recyclables[position]
        return recyclable.id
    }

    override fun getItemViewType(position: Int): Int {
        val recyclable: Recyclable = recyclerData.recyclables[position]
        return recyclable.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val recyclable: Recyclable = recyclerData.recyclables[position]
        holder.bindItem(recyclable)
    }

    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        attachRecyclerView(recyclerView)
        refreshDecorationsInRecyclerView(recyclerView, recyclerData.decorations)
    }

    @VisibleForTesting
    fun attachRecyclerView(recyclerView: RecyclerView) {
        attachedRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
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
            val currentDiffConfig: AsyncDifferConfig<Identifiable>? = itemsDiffer?.config
            if (currentDiffConfig?.backgroundThreadExecutor != diffExecutor) {
                itemsDiffer = ConfigAsyncListDiffer(
                    AdapterListUpdateCallback(this),
                    AsyncDifferConfig.Builder(IdentifiablesDiffUtilItemCallback())
                        .setBackgroundThreadExecutor(diffExecutor)
                        .build()
                )
            }
        }
    }

    private fun refreshItemsInAdapter(oldItems: List<Recyclable>, newItems: List<Recyclable>) {
        val differ: AsyncListDiffer<Identifiable>? = itemsDiffer
        if (differ != null) {
            differ.submitList(newItems)
        } else {
            refreshItemsInAdapterSync(oldItems, newItems)
        }
    }

    private fun refreshItemsInAdapterSync(oldItems: List<Recyclable>, newItems: List<Recyclable>) {
        val diffCallback: DiffUtil.Callback = IdentifiablesDiffUtilCallback(oldItems, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun refreshDecorationsInRecyclerView(recyclerView: RecyclerView, newDecorations: List<ItemDecoration>) {
        decorationUpdater.updateDecorations(recyclerView, newDecorations)
    }

}
