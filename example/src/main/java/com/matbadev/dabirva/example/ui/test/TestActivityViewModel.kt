package com.matbadev.dabirva.example.ui.test

import android.os.Parcelable
import com.matbadev.dabirva.DabirvaData
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.util.NonNullObservableField

class TestActivityViewModel : BaseScreenViewModel<Parcelable, TestActivityEvent>() {

    val dabirvaData = NonNullObservableField(DabirvaData())

}
