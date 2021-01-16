package com.matbadev.dabirva

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicReference

class DataBindingViewHolder(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val boundViewModelRef: AtomicReference<ItemViewModel?> = AtomicReference()

    val boundViewModel: ItemViewModel?
        get() = boundViewModelRef.get()

    fun bindViewModel(viewModel: ItemViewModel) {
        if (boundViewModelRef.compareAndSet(null, viewModel)) {
            binding.setVariable(viewModel.bindingId, viewModel)
            binding.executePendingBindings()
        } else {
            throw IllegalStateException("Already bound")
        }
    }

    fun unbind() {
        if (boundViewModelRef.getAndSet(null) != null) {
            binding.unbind()
        }
    }

}
