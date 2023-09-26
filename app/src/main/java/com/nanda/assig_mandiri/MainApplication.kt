package com.nanda.assig_mandiri

import android.app.Application
import com.nanda.assig_mandiri.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    private fun setupKoin() {
        val koinApp = KoinAndroidApplication.create(this, Level.NONE)
            .modules(Modules.getAppComponents())
            .androidContext(this)

        startKoin(koinApp)
    }
}