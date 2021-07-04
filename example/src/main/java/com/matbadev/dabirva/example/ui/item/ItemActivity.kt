package com.matbadev.dabirva.example.ui.item

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class ItemActivity : BaseActivity<ItemActivityArguments, ItemActivityEvent, ItemActivityViewModel>(
    viewModelClass = ItemActivityViewModel::class,
    layoutId = R.layout.activity_item,
) {

    override fun buildViewModel(repositories: AppRepositories): ItemActivityViewModel {
        return ItemActivityViewModel()
    }

    override fun handleScreenUiEvent(event: ItemActivityEvent) {
    }

}
