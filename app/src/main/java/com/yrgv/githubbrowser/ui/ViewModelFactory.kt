package com.yrgv.githubbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yrgv.githubbrowser.data.network.GithubApi

/**
 * Responsible for constructing the view models
 */
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(GithubApi.getInstance()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class :${modelClass.canonicalName}")
    }

}