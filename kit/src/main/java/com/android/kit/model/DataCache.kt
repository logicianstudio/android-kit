package com.android.kit.model

import androidx.collection.LruCache
import com.android.kit.BuildConfig
import kotlin.math.abs


class DataCache<T>(
    private val durationInMillis: Long = if (BuildConfig.DEBUG) 0L else  60 * 60 * 24
) {
    companion object {
        private val cacheSize = 50 * 1024 * 1024 // 50 MB
        private val SYNC = Object()
    }

    private val cache: LruCache<String, Pair<T, Long>> by lazy { LruCache(cacheSize) }

    operator fun set(key: String, data: T) {
        synchronized(SYNC) {
            cache.put(key, Pair(data, System.currentTimeMillis()))
        }
    }

    operator fun get(key: String): T? {
        synchronized(SYNC) {
            val (data, time) = cache.get(key) ?: Pair(null, 0L)
            return if (data != null && abs(System.currentTimeMillis() - time) <= durationInMillis) {
                data
            } else {
                null
            }
        }
    }
}