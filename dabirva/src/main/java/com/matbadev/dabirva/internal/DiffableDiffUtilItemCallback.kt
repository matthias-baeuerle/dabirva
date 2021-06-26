package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import com.matbadev.dabirva.Diffable

internal class DiffableDiffUtilItemCallback : DiffUtil.ItemCallback<Diffable>() {

    override fun areItemsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return oldItem.entityEquals(newItem)
    }

    override fun areContentsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return oldItem == newItem
    }

}
