package com.matbadev.dabirva.example.base

import android.os.Parcel
import android.os.Parcelable

abstract class BaseScreenArguments : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    // Overwrite to mark dest non-null
    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

}
