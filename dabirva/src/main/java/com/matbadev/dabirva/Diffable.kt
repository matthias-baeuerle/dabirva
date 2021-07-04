package com.matbadev.dabirva

import androidx.recyclerview.widget.DiffUtil

/**
 * Defines methods for comparing objects using [DiffUtil].
 *
 * It is recommended to implement inherited classes as `data` classes
 * to benefit from a default [equals] and [hashCode] implementation.
 */
interface Diffable {

    /**
     * Determines whether this object represents the same entity as [other].
     *
     * This is used by [Dabirva] after changes of the list content
     * to find the new position of a list item.
     */
    fun entityEquals(other: Any?): Boolean

    /**
     * Determines whether this object is equal to [other] in terms of class and data.
     *
     * This is used by [Dabirva] after changes of the list content
     * to determine if the data of a specific item has changed
     * in which case the data bindings for the corresponding item
     * will be executed again.
     * The implementation should therefore **only consider fields which actually require a rebind**,
     * e.g. a click listener should **not** be considered because once a click event occurs
     * it is always the latest value of the listener property that will be called,
     * also without a rebind.
     */
    override operator fun equals(other: Any?): Boolean

    /**
     * Returns a hash code value for this object.
     *
     * This method should be implemented as specified in [Object.hashCode].
     * It is only part of [Diffable] to force consistent implementations of this method
     * and the mandatory [equals] implementation.
     */
    override fun hashCode(): Int

}
