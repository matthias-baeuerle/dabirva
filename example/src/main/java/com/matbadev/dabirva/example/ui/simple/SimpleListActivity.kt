package com.matbadev.dabirva.example.ui.simple

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class SimpleListActivity : BaseActivity<SimpleListEvent, SimpleListViewModel>(
    viewModelClass = SimpleListViewModel::class,
    layoutId = R.layout.activity_simple_list,
) {

    override fun buildViewModel(repositories: AppRepositories): SimpleListViewModel {
        return SimpleListViewModel(
            synchronousNoteRepository = repositories.synchronousNoteRepository,
        )
    }

    override fun handleScreenUiEvent(event: SimpleListEvent) {
    }

}
