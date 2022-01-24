package com.android.kit.ui.application

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import com.android.kit.BuildConfig
import com.android.kit.ktx.appName
import com.android.kit.logI
import com.android.kit.preference.KitPreference
import com.squareup.picasso.Picasso


abstract class KitApplication : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        var instance: KitApplication? = null
    }

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        instance = this

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
        logI("Application in background")
    }

    open fun onResume() {
        logI("Application in foreground")
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            onResume()
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations;
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            onPause()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}