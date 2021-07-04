package com.matbadev.dabirva

import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var boundViewModel: ItemViewModel? = null
        private set

    @MainThread
    fun bindViewModel(viewModel: ItemViewModel) {
        if (!binding.setVariable(viewModel.bindingId, viewModel)) {
            throw UnsupportedOperationException("Variable with ID ${viewModel.bindingId} is not used in layout with ID ${viewModel.layoutId}")
        }
        binding.executePendingBindings()
        boundViewModel = viewModel
    }

    @MainThread
    fun unbind() {
        boundViewModel = null
        binding.unbind()
    }

}
