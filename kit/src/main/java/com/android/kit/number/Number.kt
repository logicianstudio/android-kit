package com.android.kit.number

fun Double.format(decimals: Int) = java.lang.String.format("%.${decimals}f", this)
fun Float.format(decimals: Int) = java.lang.String.format("%.${decimals}f", this)