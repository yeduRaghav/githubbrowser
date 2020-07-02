package com.yrgv.githubbrowser.util.resource

import androidx.annotation.StringRes

/**
 * Defines the behaviours guaranteed by a resource provider.
 * This helps ViewModels still access the resources but not have to deal with context.
 */
interface ResourceProvider {
    fun getString(@StringRes resId: Int): String?
}