package com.matbadev.dabirva.example.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onLongClick")
fun setViewOnLongClickListener(view: View, onLongClick: View.OnLongClickListener?) {
    view.setOnLongClickListener(onLongClick)
}
