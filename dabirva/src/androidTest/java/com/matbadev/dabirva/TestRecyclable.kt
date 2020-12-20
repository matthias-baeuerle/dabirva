package com.matbadev.dabirva

data class TestRecyclable(
    override val id: Long,
    val data: String? = null,
) : Recyclable {

    override val layoutId: Int = 1

    override val bindingId: Int = 1

}
