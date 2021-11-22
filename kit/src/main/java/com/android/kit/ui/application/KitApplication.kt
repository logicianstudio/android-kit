package com.android.kit.ui.application

import android.app.Application
import com.android.kit.BuildConfig
import com.android.kit.preference.KitPreference
import com.squareup.picasso.Picasso

abstract class KitApplication : Application() {

    companion object {
        var instance: KitApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KitPreference.init(this)

        if (BuildConfig.DEBUG) {
            with(Picasso.get()) {
                isLoggingEnabled = true
                setIndicatorsEnabled(true)
            }
        }
    }

}