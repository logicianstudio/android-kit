package com.android.kit.resource

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.getColorResource(name: String): Int {
    return resources.getIdentifier(name, "color", packageName)
}

fun Context.getColorCompat(name: String): Int {
    return ContextCompat.getColor(this, getColorResource(name))
}

fun Context.getColorCompat(res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Activity.getColorResource(name: String): Int {
    return resources.getIdentifier(name, "color", packageName)
}

fun Activity.getColorCompat(name: String): Int {
    return ContextCompat.getColor(this, getColorResource(name))
}

fun Fragment.getColorResource(name: String): Int {
    return requireContext().getColorResource(name)
}

fun Fragment.getColorCompat(name: String): Int {
    return requireContext().getColorCompat(name)
}