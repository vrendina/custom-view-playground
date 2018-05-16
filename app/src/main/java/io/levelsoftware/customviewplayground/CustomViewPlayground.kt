package io.levelsoftware.customviewplayground

import android.app.Application
import timber.log.Timber

class CustomViewPlayground: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}