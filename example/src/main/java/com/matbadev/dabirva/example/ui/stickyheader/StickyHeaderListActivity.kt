package com.matbadev.dabirva.example.ui.stickyheader

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class StickyHeaderListActivity : BaseActivity<StickyHeaderListEvent, StickyHeaderListArguments, StickyHeaderListViewModel>(
    viewModelClass = StickyHeaderListViewModel::class,
    layoutId = R.layout.activity_sticky_header_list,
) {

    override fun buildViewModel(repositories: AppRepositories): StickyHeaderListViewModel {
        return StickyHeaderListViewModel(
            noteRepository = repositories.noteRepository,
        )
    }

    override fun handleScreenUiEvent(event: StickyHeaderListEvent) {
    }

}
