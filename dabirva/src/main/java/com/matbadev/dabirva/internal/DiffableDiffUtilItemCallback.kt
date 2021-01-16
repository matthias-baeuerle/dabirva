package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import com.matbadev.dabirva.Diffable

internal class DiffableDiffUtilItemCallback : DiffUtil.ItemCallback<Diffable>() {

    override fun areItemsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Diffable, newItem: Diffable): Boolean {
        return oldItem == newItem
    }

}
