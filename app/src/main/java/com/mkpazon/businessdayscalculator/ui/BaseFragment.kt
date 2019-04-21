package com.mkpazon.businessdayscalculator.ui

import androidx.fragment.app.Fragment
import com.mkpazon.businessdayscalculator.App
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseFragment : Fragment(), KodeinAware {
    override val kodein by kodein(App.context)
    protected val compositeDisposable by lazy { CompositeDisposable() }
}