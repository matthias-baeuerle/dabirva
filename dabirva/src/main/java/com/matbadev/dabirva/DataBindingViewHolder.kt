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
        binding.setVariable(viewModel.bindingId, viewModel)
        binding.executePendingBindings()
        boundViewModel = viewModel
    }

    @MainThread
    fun unbind() {
        boundViewModel = null
        binding.unbind()
    }

}
