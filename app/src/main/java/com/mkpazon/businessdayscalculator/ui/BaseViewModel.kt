package com.mkpazon.businessdayscalculator.ui

import androidx.lifecycle.ViewModel
import com.mkpazon.businessdayscalculator.App
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseViewModel: ViewModel(), KodeinAware {
    override val kodein by kodein(App.context)
}