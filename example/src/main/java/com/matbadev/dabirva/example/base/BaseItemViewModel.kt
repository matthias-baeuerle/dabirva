package com.matbadev.dabirva.example.base

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR

abstract class BaseItemViewModel : ItemViewModel {

    final override val bindingId: Int = BR.viewModel

}
