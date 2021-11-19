package com.android.kit.ui.application

import android.app.Application
import com.android.kit.BuildConfig
import com.android.kit.preference.PreferenceKit
import com.squareup.picasso.Picasso

abstract class ApplicationKit : Application() {

    companion object {
        var instance: ApplicationKit? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferenceKit.init(this)

        if (BuildConfig.DEBUG) {
            with(Picasso.get()) {
                isLoggingEnabled = true
                setIndicatorsEnabled(true)
            }
        }
    }

}