package com.mkpazon.businessdayscalculator.util.ext

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    activity?.let { Toast.makeText(it, message, length).show() }
}