package com.matbadev.dabirva.example.ui.diffing

import android.os.Parcelable
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.util.NonNullObservableField

class ItemDiffingActivityViewModel : BaseScreenViewModel<Parcelable, ItemDiffingActivityEvent>() {

    val dabirvaData = NonNullObservableField(DabirvaData())

}
