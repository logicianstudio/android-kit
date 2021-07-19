package com.android.kit.model

import androidx.appcompat.app.AppCompatDelegate

data class NightMode(
    val title: String = "Off",
    val mode: Int = AppCompatDelegate.MODE_NIGHT_NO
){
    companion object{
        val OFF = NightMode()
    }
}