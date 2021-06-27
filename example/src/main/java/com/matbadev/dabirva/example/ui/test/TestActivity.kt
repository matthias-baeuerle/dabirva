package com.matbadev.dabirva.example.ui.test

import android.os.Parcelable
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class TestActivity : BaseActivity<Parcelable, TestActivityEvent, TestActivityViewModel>(
    viewModelClass = TestActivityViewModel::class,
    layoutId = R.layout.activity_test,
) {

    override fun buildViewModel(repositories: AppRepositories): TestActivityViewModel {
        return TestActivityViewModel()
    }

    override fun handleScreenUiEvent(event: TestActivityEvent) {
    }

}
