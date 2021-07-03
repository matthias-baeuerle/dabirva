package com.matbadev.dabirva.example.ui.test

import android.os.Parcelable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import com.matbadev.dabirva.util.NonNullObservableField
import java.util.concurrent.Executor

class TestActivityViewModel : BaseScreenViewModel<Parcelable, TestActivityEvent>() {

    val items = NonNullObservableField<List<ItemViewModel>>(listOf())

    val itemDecorations = NonNullObservableField<List<RecyclerView.ItemDecoration>>(listOf())

    val diffExecutor = ObservableField<Executor?>()

}
