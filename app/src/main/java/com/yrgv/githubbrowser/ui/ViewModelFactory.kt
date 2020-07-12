package com.yrgv.githubbrowser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.data.network.endpoints.GetUserInfoEndpoint
import com.yrgv.githubbrowser.data.network.endpoints.GetUserReposEndpoint
import com.yrgv.githubbrowser.util.resource.DefaultResourceProvider

/**
 * Responsible for constructing the view models
 */
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(
                DefaultResourceProvider.getInstance(application),
                GetUserInfoEndpoint(GithubApi.getInstance()),
                GetUserReposEndpoint(GithubApi.getInstance())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class :${modelClass.canonicalName}")
    }

}