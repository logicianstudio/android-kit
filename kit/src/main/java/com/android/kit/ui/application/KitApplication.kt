package com.android.kit.ui.application

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.kit.BuildConfig
import com.android.kit.ktx.appName
import com.android.kit.logD
import com.android.kit.logI
import com.android.kit.preference.KitPreference
import com.squareup.picasso.Picasso


abstract class KitApplication : Application() {

    companion object {
        var instance: KitApplication? = null
    }

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    private val lifecycleListener: SampleLifecycleListener by lazy {
        SampleLifecycleListener()
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
        val preferenceName = "${appName.lowercase().replace(" ", "_")}.pref"
        KitPreference.init(this, preferenceName)

        if (BuildConfig.DEBUG) {
            with(Picasso.get()) {
                isLoggingEnabled = true
                setIndicatorsEnabled(true)
            }
        }
    }

    open fun onPause() {
        logI("onPause")
    }

    open fun onResume() {
        logI("onResume")
    }

    open fun onStart() {
        logI("onStart")
    }

    open fun onStop() {
        logI("onStop")
    }

    inner class SampleLifecycleListener : DefaultLifecycleObserver {

        override fun onResume(owner: LifecycleOwner) {
            this@KitApplication.onResume()
        }

        override fun onStart(owner: LifecycleOwner) {
            this@KitApplication.onStart()
        }

        override fun onStop(owner: LifecycleOwner) {
            this@KitApplication.onStop()
        }

        override fun onPause(owner: LifecycleOwner) {
            logD("onPause: Moving to background…")
            this@KitApplication.onPause()
        }
    }

}