package com.hsd.contest.spain

import android.app.Application
import com.hsd.contest.data.di.DataKoinConfiguration
import com.hsd.contest.data.di.RetrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    RetrofitModule().getModule(),
                    DataKoinConfiguration().getModule(),
                    //DomainKoinConfiguration().getModule(),
                    //PresentationKoinConfiguration().getModule()
                )
            )
        }

    }
}