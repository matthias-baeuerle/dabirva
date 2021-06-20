package com.matbadev.dabirva.example.ui.diffing

import android.os.Parcelable
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class ItemDiffingActivity : BaseActivity<Parcelable, ItemDiffingActivityEvent, ItemDiffingActivityViewModel>(
    viewModelClass = ItemDiffingActivityViewModel::class,
    layoutId = R.layout.activity_item_diffing,
) {

    override fun buildViewModel(repositories: AppRepositories): ItemDiffingActivityViewModel {
        return ItemDiffingActivityViewModel()
    }

    override fun handleScreenUiEvent(event: ItemDiffingActivityEvent) {
    }

}
