package com.yrgv.githubbrowser.util

import android.util.Log
import com.yrgv.githubbrowser.BuildConfig

/**
 * Functions to help debug
 */
fun logThread(title: String) {
    if (!BuildConfig.DEBUG) return
    Log.d("appan", "$title ==> ${Thread.currentThread().name}")
}



