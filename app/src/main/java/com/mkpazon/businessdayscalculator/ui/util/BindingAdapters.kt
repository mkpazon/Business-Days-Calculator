package com.mkpazon.businessdayscalculator.ui.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.mkpazon.businessdayscalculator.util.ext.orFalse

@BindingAdapter("android:visibility")
fun setViewVisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) View.VISIBLE else View.GONE
}
