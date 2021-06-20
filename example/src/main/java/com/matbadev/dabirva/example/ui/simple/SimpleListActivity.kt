package com.matbadev.dabirva.example.ui.simple

import android.os.Parcelable
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class SimpleListActivity : BaseActivity<Parcelable, SimpleListActivityEvent, SimpleListActivityViewModel>(
    viewModelClass = SimpleListActivityViewModel::class,
    layoutId = R.layout.activity_simple_list,
) {

    override fun buildViewModel(repositories: AppRepositories): SimpleListActivityViewModel {
        return SimpleListActivityViewModel(
            noteRepository = repositories.noteRepository,
        )
    }

    override fun handleScreenUiEvent(event: SimpleListActivityEvent) {
    }

}
