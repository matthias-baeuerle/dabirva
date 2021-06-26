package com.matbadev.dabirva

interface Diffable {

    fun entityEquals(other: Any?): Boolean

    override operator fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}
