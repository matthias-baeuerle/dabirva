package com.matbadev.dabirva.example.ui.simple

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenArguments

class SimpleListActivityArguments : BaseScreenArguments() {

    companion object CREATOR : Parcelable.Creator<SimpleListActivityArguments> {
        override fun createFromParcel(parcel: Parcel) = SimpleListActivityArguments()
        override fun newArray(size: Int): Array<SimpleListActivityArguments?> = arrayOfNulls(size)
    }

}
