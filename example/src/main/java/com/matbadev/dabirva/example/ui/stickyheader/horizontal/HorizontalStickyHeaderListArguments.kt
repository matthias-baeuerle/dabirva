package com.matbadev.dabirva.example.ui.stickyheader.horizontal

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenArguments

class HorizontalStickyHeaderListArguments : BaseScreenArguments() {

    companion object CREATOR : Parcelable.Creator<HorizontalStickyHeaderListArguments> {
        override fun createFromParcel(parcel: Parcel) = HorizontalStickyHeaderListArguments()
        override fun newArray(size: Int): Array<HorizontalStickyHeaderListArguments?> = arrayOfNulls(size)
    }

}
