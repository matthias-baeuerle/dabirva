package com.matbadev.dabirva.example.ui.customfactory

import android.os.Bundle
import android.os.Parcelable
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class CustomFactoryActivity : BaseActivity<Parcelable, CustomFactoryActivityEvent, CustomFactoryActivityViewModel>(
    viewModelClass = CustomFactoryActivityViewModel::class,
    layoutId = R.layout.activity_custom_factory,
) {

    override fun buildViewModel(repositories: AppRepositories): CustomFactoryActivityViewModel {
        return CustomFactoryActivityViewModel()
    }

    override fun handleScreenUiEvent(event: CustomFactoryActivityEvent) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DabirvaConfig.factory = DabirvaFactory { CustomDabirva() }
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        DabirvaConfig.factory = DabirvaFactory { Dabirva() }
    }

}
