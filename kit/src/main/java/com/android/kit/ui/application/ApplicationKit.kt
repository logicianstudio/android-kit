package com.android.kit.ui.application

import android.app.Application
import com.android.kit.preference.PreferenceKit

abstract class ApplicationKit : Application() {

    companion object {
        var instance: ApplicationKit? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferenceKit.init(this)
    }

}