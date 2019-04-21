package com.mkpazon.businessdayscalculator.di

import com.mkpazon.businessdayscalculator.DEFAULT_DATE_FORMAT
import com.mkpazon.businessdayscalculator.data.repository.Repository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import java.text.SimpleDateFormat
import java.util.*


val appModule = Kodein.Module("appModule") {
    bind<Repository>() with singleton { Repository() }
    bind<SimpleDateFormat>() with singleton { SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US) }
}
