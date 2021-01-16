package com.matbadev.dabirva.example.ui.stickyheader.vertical

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenArguments

class VerticalStickyHeaderListArguments : BaseScreenArguments() {

    companion object CREATOR : Parcelable.Creator<VerticalStickyHeaderListArguments> {
        override fun createFromParcel(parcel: Parcel) = VerticalStickyHeaderListArguments()
        override fun newArray(size: Int): Array<VerticalStickyHeaderListArguments?> = arrayOfNulls(size)
    }

}
