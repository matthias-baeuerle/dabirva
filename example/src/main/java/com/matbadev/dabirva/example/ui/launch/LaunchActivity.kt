package com.matbadev.dabirva.example.ui.launch

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class LaunchActivity : BaseActivity<LaunchActivityEvent, LaunchActivityViewModel>(
    viewModelClass = LaunchActivityViewModel::class,
    layoutId = R.layout.activity_launch,
) {

    override fun buildViewModel(repositories: AppRepositories): LaunchActivityViewModel {
        return LaunchActivityViewModel()
    }

    override fun handleScreenUiEvent(event: LaunchActivityEvent) {
    }

}
