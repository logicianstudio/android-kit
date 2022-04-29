package com.android.kit

import android.util.Log
import java.lang.Exception

fun Any.logV(message: String) {
    logV(this::class.java.simpleName, message)
}

fun Any.logE(message: String) {
    logE(this::class.java.simpleName, message)
}

fun Any.logD(message: String) {
    logD(this::class.java.simpleName, message)
}

fun Any.logI(message: String) {
    logI(this::class.java.simpleName, message)
}

fun Any.logWtf(message: String) {
    logWtf(this::class.java.simpleName, message)
}

fun Any.logW(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.logV(tag: String, message: String) {
    Log.v(tag, message)
}

fun Any.logE(message: String, e: Exception?) {
    logE(this::class.java.simpleName, message, e)
}

fun Any.logE(tag: String, message: String) {
    Log.e(tag, message)
}

fun Any.logE(tag: String, message: String, e: Exception?) {
    Log.e(tag, message, e)
}

fun Any.logD(tag: String, message: String) {
    Log.d(tag, message)
}

fun Any.logI(tag: String, message: String) {
    Log.i(tag, message)
}

fun Any.logW(tag: String, message: String) {
    Log.w(tag, message)
}

fun Any.logWtf(tag: String, message: String) {
    Log.wtf(tag, message)
}

fun Any.logMethodName(message: String) {
    val methodName = if (Thread.currentThread().stackTrace.size > 3) {
        Thread.currentThread().stackTrace[3].methodName
    } else {
        ""
    }
    logI("$methodName : $message")
}