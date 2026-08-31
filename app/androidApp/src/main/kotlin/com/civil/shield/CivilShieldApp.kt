package com.civil.shield

import android.app.Application
import com.civil.shield.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CivilShieldApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CivilShieldApp)
        }
    }
}
