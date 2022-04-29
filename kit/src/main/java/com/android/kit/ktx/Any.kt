package com.android.kit.ktx

import android.os.Handler
import android.os.Looper

fun Any.throwException(message: String): Nothing = throw Exception("${javaClass.simpleName}: $message")

fun Any.postDelayed(delay: Long, task: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(task, delay)
}