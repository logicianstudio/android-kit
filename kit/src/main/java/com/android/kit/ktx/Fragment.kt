package com.android.kit.ktx

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment

fun Fragment.runOnUiThread(runnable: Runnable) = Handler(Looper.getMainLooper()).post(runnable)