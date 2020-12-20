package com.matbadev.dabirva

import android.os.Parcelable

/**
 * Special implementation of [BindableLayout] which also requires an ID.
 */
interface Recyclable : BindableLayout, Identifiable
