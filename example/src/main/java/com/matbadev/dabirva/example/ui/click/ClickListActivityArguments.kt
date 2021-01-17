package com.matbadev.dabirva.example.ui.click

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenArguments

class ClickListActivityArguments : BaseScreenArguments() {

    companion object CREATOR : Parcelable.Creator<ClickListActivityArguments> {

        override fun createFromParcel(parcel: Parcel) = ClickListActivityArguments()
        override fun newArray(size: Int): Array<ClickListActivityArguments?> = arrayOfNulls(size)
    }

}
