package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.matbadev.dabirva.internal.DecorationDiffUtilCallback
import com.matbadev.dabirva.internal.DecorationListUpdateCallback
import com.matbadev.dabirva.internal.DecorationListUpdateCallbackDelegate
import com.matbadev.dabirva.internal.IdentifiablesDiffUtilCallback
import com.matbadev.dabirva.internal.collectDecorations
import java.lang.AssertionError

class Dabirva(
    initialRecyclerData: RecyclerData,
) : RecyclerView.Adapter<DataBindingViewHolder>() {

    var recyclerData: RecyclerData = initialRecyclerData
        set(newData) {
            val oldData: RecyclerData = field
            field = newData
            refreshItemsInAdapter(oldData.recyclables, newData.recyclables)
            attachedRecyclerView?.let { recyclerView: RecyclerView ->
                refreshDecorationsInRecyclerView(recyclerView, newData.decorations)
            }
        }

    private var attachedRecyclerView: RecyclerView? = null

    init {
        setHasStableIds(true)
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

    private fun refreshItemsInAdapter(oldItems: List<Recyclable>, newItems: List<Recyclable>) {
        val diffCallback: DiffUtil.Callback = IdentifiablesDiffUtilCallback(oldItems, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun refreshDecorationsInRecyclerView(recyclerView: RecyclerView, newDecorations: List<ItemDecoration>) {
        val oldDecorations: List<ItemDecoration> = recyclerView.collectDecorations()
        val diffCallback: DiffUtil.Callback = DecorationDiffUtilCallback(oldDecorations, newDecorations)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        val updateCallbackDelegate = object : DecorationListUpdateCallbackDelegate {
            override fun getDecoration(newPosition: Int): ItemDecoration {
                val decorationPosition = diffResult.convertNewPositionToOld(newPosition)
                if (decorationPosition == DiffUtil.DiffResult.NO_POSITION) throw AssertionError()
                return newDecorations[newPosition]
            }
        }
        val updateCallback: ListUpdateCallback = DecorationListUpdateCallback(recyclerView, updateCallbackDelegate)
        diffResult.dispatchUpdatesTo(updateCallback)
    }

}
