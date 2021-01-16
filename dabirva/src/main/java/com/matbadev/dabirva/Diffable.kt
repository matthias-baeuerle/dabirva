package com.matbadev.dabirva

interface Diffable {

    val id: Long

    override operator fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}
