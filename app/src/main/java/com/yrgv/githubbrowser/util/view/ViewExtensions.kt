package com.yrgv.githubbrowser.util.view

import android.view.View
import com.yrgv.githubbrowser.util.SimpleCallback

/**
 * Extension functions for the View classes for convenience
 */

/**
 * Extension functions for the View classes for convenience
 */
fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Simple debounce implementation
 * */
fun View.setThrottledClickListener(delayInMillis: Long = 500L, runWhenClicked: SimpleCallback) {
    setOnClickListener {
        this.isClickable = false
        this.postDelayed(
            {
                this.isClickable = true
            },
            delayInMillis
        )
        runWhenClicked()
    }
}