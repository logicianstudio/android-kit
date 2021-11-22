package com.android.kit.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.android.kit.model.NightMode
import com.google.gson.Gson

object KitPreference {
    private const val PREFERENCE_NAME = "AndroidKit.Preference"
    private const val DARK_MODE = "Pref.DARK_MODE"

    private var gson = Gson()
    private lateinit var preference: SharedPreferences

    fun init(context: Context) {
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var nightMode: NightMode
        set(value) {
            set(DARK_MODE, gson.toJson(value, NightMode::class.java))
        }
        get() {
            val str = getString(DARK_MODE)
            return if (str == null) {
                NightMode.OFF
            } else {
                gson.fromJson(str, NightMode::class.java)
            }
        }

    fun set(key: String, value: Int, commit: Boolean = true) {
        preference.edit(commit) {
            putInt(key, value)
        }
    }

    fun set(key: String, value: Float, commit: Boolean = true) {
        preference.edit(commit) {
            putFloat(key, value)
        }
    }

    fun set(key: String, value: Long, commit: Boolean = true) {
        preference.edit(commit) {
            putLong(key, value)
        }
    }

    fun set(key: String, value: String, commit: Boolean = true) {
        preference.edit(commit) {
            putString(key, value)
        }
    }

    fun set(key: String, value: MutableSet<String>, commit: Boolean = true) {
        preference.edit(commit) {
            putStringSet(key, value)
        }
    }

    fun set(key: String, value: Boolean, commit: Boolean = true) {
        preference.edit(commit) {
            putBoolean(key, value)
        }
    }

    fun remove(key: String) {
        preference.edit {
            remove(key)
        }
    }

    fun contains(key: String): Boolean {
        return preference.contains(key)
    }

    fun getInt(key: String): Int {
        return preference.getInt(key, 0)
    }

    fun getLong(key: String): Long {
        return preference.getLong(key, 0)
    }

    fun getFloat(key: String, defValue: Float = 0f): Float {
        return preference.getFloat(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return preference.getBoolean(key, defValue)
    }

    fun getString(key: String): String? {
        return preference.getString(key, null)
    }

    fun getStringSet(key: String): MutableSet<String>? {
        return preference.getStringSet(key, null)
    }
}