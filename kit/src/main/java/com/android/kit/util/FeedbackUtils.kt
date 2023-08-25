package com.android.kit.util

import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.content.Intent.ACTION_SENDTO
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.createChooser
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import com.android.kit.R
import java.util.*

object FeedbackUtils {

    fun supportEmail(
        context: Context,
        subject: String = emailSubject(context),
        email: String,
    ) {
        supportEmail(context, subject, arrayOf(email))
    }

    fun supportEmail(
        context: Context,
        subject: String = emailSubject(context),
        emails: Array<String>,
    ) {
        val intent = Intent(ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(EXTRA_SUBJECT, subject)
            putExtra(EXTRA_EMAIL, emails)
            putExtra(EXTRA_TEXT, "Detail Information:\n\n" + getDeviceInfo(context))
        }
        context.startActivity(createChooser(intent, "Send E-Mail"))
    }

    private fun emailSubject(context: Context): String {
        try {
            val pInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            val name = getApplicationName(context)
            val plateForm = "Android"
            //            Toast.makeText(context, "nameVersion"+name+version, Toast.LENGTH_SHORT).show();
            return "Support: $name - $version ($plateForm)"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return context.getString(R.string.app_name)
    }

    fun getApplicationName(context: Context): String {
        val applicationInfo = context.applicationInfo
        val stringId = applicationInfo.labelRes
        var appName =
            if (stringId == 0) {
                applicationInfo.nonLocalizedLabel.toString()
            } else {
                context.getString(
                    stringId,
                )
            }
        if (appName.isEmpty()) {
            appName = context.getString(R.string.app_name)
        }
        //        Toast.makeText(context, "name"+appName, Toast.LENGTH_SHORT).show();
        return appName
    }

    fun getDeviceInfo(context: Context): String {
        val sdk = Build.VERSION.SDK_INT // API Level
        val model = Build.MODEL // Model
        val brand = Build.BRAND // Product
        var infoString = ""
        val locale = getCountryNameWithCode(context)
//        try {
//            val pInfo =
//                context.packageManager.getPackageInfo(context.packageName, 0)
//            val version = pInfo.versionName
//            infoString += "App Version:\t $version\n"
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
        infoString += "Android:\t $sdk\n"
        infoString += "Device:\t $brand ($model), ${getDeviceType(context)}\n"
        if (!locale.isNullOrEmpty()) {
            infoString += "Country:\t $locale\n\n"
        }
        return infoString
    }

    fun getCountryName(context: Context): String? {
        return getCountryCode(context)?.let { code ->
            Locale("", code).displayCountry
        }
    }

    fun getCountryNameWithCode(context: Context): String? {
        return getCountryCode(context)?.let { code ->
            "${Locale("", code).displayCountry} ($code)"
        }
    }

    fun getCountryCode(context: Context): String? {
        val telephoneManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        return telephoneManager.networkCountryIso
    }

    fun getDeviceType(context: Context): String {
        val tabletSize =
            context.resources.getBoolean(R.bool.isTablet)
        return if (tabletSize) {
            "Tablet"
        } else {
            "Phone"
        }
    }
}
