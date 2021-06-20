package com.matbadev.dabirva.example.ui.click

import android.os.Parcelable
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class ClickListActivity : BaseActivity<Parcelable, ClickListActivityEvent, ClickListActivityViewModel>(
    viewModelClass = ClickListActivityViewModel::class,
    layoutId = R.layout.activity_click_list,
) {

    override fun buildViewModel(repositories: AppRepositories): ClickListActivityViewModel {
        return ClickListActivityViewModel(
            noteRepository = repositories.noteRepository,
        )
    }

    override fun handleScreenUiEvent(event: ClickListActivityEvent) {
    }

}
