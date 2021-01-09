package com.matbadev.dabirva.example.ui.stickyheader

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.LinearOrientation
import com.matbadev.dabirva.example.base.BaseScreenArguments

data class StickyHeaderListArguments(
    val listOrientation: LinearOrientation,
) : BaseScreenArguments() {

    constructor(parcel: Parcel) : this(
        listOrientation = LinearOrientation.valueOf(parcel.readString()!!),
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(listOrientation.name)
    }

    companion object CREATOR : Parcelable.Creator<StickyHeaderListArguments> {
        override fun createFromParcel(parcel: Parcel) = StickyHeaderListArguments(parcel)
        override fun newArray(size: Int): Array<StickyHeaderListArguments?> = arrayOfNulls(size)
    }

}
