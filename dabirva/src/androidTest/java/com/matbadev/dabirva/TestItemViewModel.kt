package com.matbadev.dabirva

data class TestItemViewModel(
    override val id: Long,
    val data: String? = null,
) : ItemViewModel {

    override val layoutId: Int = 1

    override val bindingId: Int = 1

}
