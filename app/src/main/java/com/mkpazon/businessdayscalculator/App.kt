package com.mkpazon.businessdayscalculator

import android.app.Application
import com.mkpazon.businessdayscalculator.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {

    companion object {
        lateinit var context: App
    }

    override val kodein = Kodein.lazy {
        import(appModule)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}