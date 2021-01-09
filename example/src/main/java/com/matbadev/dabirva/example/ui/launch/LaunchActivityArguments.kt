package com.matbadev.dabirva.example.ui.launch

import android.os.Parcel
import android.os.Parcelable
import com.matbadev.dabirva.example.base.BaseScreenArguments

class LaunchActivityArguments : BaseScreenArguments() {

    companion object CREATOR : Parcelable.Creator<LaunchActivityArguments> {
        override fun createFromParcel(parcel: Parcel) = LaunchActivityArguments()
        override fun newArray(size: Int): Array<LaunchActivityArguments?> = arrayOfNulls(size)
    }

}
