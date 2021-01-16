package com.matbadev.dabirva.example.ui.stickyheader.horizontal

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class HorizontalStickyHeaderListActivity : BaseActivity<HorizontalStickyHeaderListEvent, HorizontalStickyHeaderListArguments, HorizontalStickyHeaderListViewModel>(
    viewModelClass = HorizontalStickyHeaderListViewModel::class,
    layoutId = R.layout.activity_sticky_header_list_horizontal,
) {

    override fun buildViewModel(repositories: AppRepositories): HorizontalStickyHeaderListViewModel {
        return HorizontalStickyHeaderListViewModel(
            noteRepository = repositories.noteRepository,
        )
    }

    override fun handleScreenUiEvent(event: HorizontalStickyHeaderListEvent) {
    }

}
