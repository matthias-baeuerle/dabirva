package com.matbadev.dabirva

interface Identifiable {

    val id: Long

    override operator fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}
