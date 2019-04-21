package com.mkpazon.businessdayscalculator.main

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges

import com.mkpazon.businessdayscalculator.R
import com.mkpazon.businessdayscalculator.databinding.FragmentMainBinding
import com.mkpazon.businessdayscalculator.ui.BaseFragment
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_main.*
import com.mkpazon.businessdayscalculator.data.model.Resource.Status.*
import com.mkpazon.businessdayscalculator.util.toCalendar
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val simpleDateFormat by instance<SimpleDateFormat>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()

        compositeDisposable += et_start_date.clicks().subscribe {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
                et_start_date.setText(simpleDateFormat.format(calendar.time))
            }
            activity?.let {
                val currentValue = et_start_date.text.toString()
                val cal = if (currentValue.isEmpty()) {
                    Calendar.getInstance()
                } else {
                    val date = simpleDateFormat.parse(currentValue)
                    date.toCalendar()
                }
                DatePickerDialog(it, listener, cal.get(YEAR), cal.get(MONTH), cal.get(DATE)).show()
            }
        }

        compositeDisposable += et_end_date.clicks().subscribe {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
                et_end_date.setText(simpleDateFormat.format(calendar.time))
            }
            activity?.let {
                val currentValue = et_end_date.text.toString()
                val cal = if (currentValue.isEmpty()) {
                    Calendar.getInstance()
                } else {
                    val date = simpleDateFormat.parse(currentValue)
                    date.toCalendar()
                }
                DatePickerDialog(it, listener, cal.get(YEAR), cal.get(MONTH), cal.get(DATE)).show()
            }
        }

        val startDateObservable: Observable<String> =
            et_start_date.textChanges().skipInitialValue().map { it.toString() }
        val endDateObservable = et_end_date.textChanges().skipInitialValue().map { it.toString() }

        compositeDisposable += Observable.combineLatest(
            startDateObservable,
            endDateObservable,
            BiFunction { t1: String, t2: String ->
                if (t1.isNotEmpty() && t2.isNotEmpty()) {
                    viewModel.getBusinessDays(t1, t2)
                }
            })
            .subscribe()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.businessDays.observe(this@MainFragment, Observer { resource ->
            when (resource.status) {
                LOADING -> {
                    binding.businessDays = resource.data ?: 0
                    binding.isLoading = true
                }
                SUCCESS -> {
                    binding.isLoading = false
                    binding.businessDays = resource.data ?: 0
                }
                ERROR -> binding.isLoading = false
            }
        })
    }
}
