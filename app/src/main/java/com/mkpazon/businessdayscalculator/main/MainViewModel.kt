package com.mkpazon.businessdayscalculator.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mkpazon.businessdayscalculator.data.model.Resource
import com.mkpazon.businessdayscalculator.data.repository.Repository
import com.mkpazon.businessdayscalculator.ui.BaseViewModel
import com.mkpazon.businessdayscalculator.util.BusinessdayUtil
import kotlinx.coroutines.*
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class MainViewModel : BaseViewModel() {

    private val repository by instance<Repository>()
    private val simpleDateFormat by instance<SimpleDateFormat>()

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val businessDaysLiveData = MutableLiveData<Resource<Int>>()
    val businessDays: LiveData<Resource<Int>> = businessDaysLiveData

    fun getBusinessDays(startDateStr: String, endDateStr: String) {
        businessDaysLiveData.value = Resource.loading()
        scope.launch {
            val startDate = simpleDateFormat.parse(startDateStr)
            val endDate = simpleDateFormat.parse(endDateStr)
            val holidays = repository.getNSWHolidaysAsync().await()
            val numOfBusinesDays = BusinessdayUtil.getNumberOfBusinessDays(startDate, endDate, holidays, inclusive = false)
            businessDaysLiveData.postValue(Resource.success(numOfBusinesDays))
        }
    }
}
