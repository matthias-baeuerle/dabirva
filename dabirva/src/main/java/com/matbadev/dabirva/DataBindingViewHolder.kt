package com.matbadev.dabirva

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicReference

class DataBindingViewHolder(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val boundRecyclableRef: AtomicReference<Recyclable?> = AtomicReference()

    val boundRecyclable: Recyclable?
        get() = boundRecyclableRef.get()

    fun bindItem(recyclable: Recyclable) {
        if (boundRecyclableRef.compareAndSet(null, recyclable)) {
            binding.setVariable(recyclable.bindingId, recyclable)
            binding.executePendingBindings()
        } else {
            throw IllegalStateException("Already bound")
        }
    }

    fun unbind() {
        if (boundRecyclableRef.getAndSet(null) != null) {
            binding.unbind()
        }
    }

}
