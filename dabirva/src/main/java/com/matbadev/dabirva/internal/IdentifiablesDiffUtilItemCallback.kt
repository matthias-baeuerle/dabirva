package com.matbadev.dabirva.internal

import androidx.recyclerview.widget.DiffUtil
import com.matbadev.dabirva.Identifiable

internal class IdentifiablesDiffUtilItemCallback : DiffUtil.ItemCallback<Identifiable>() {

    override fun areItemsTheSame(oldItem: Identifiable, newItem: Identifiable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Identifiable, newItem: Identifiable): Boolean {
        return oldItem == newItem
    }

}
