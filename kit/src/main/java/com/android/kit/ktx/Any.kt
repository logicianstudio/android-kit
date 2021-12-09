package com.android.kit.ktx

fun Any.throwException(message: String): Nothing = throw Exception("${javaClass.simpleName}: $message")