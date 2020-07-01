package com.yrgv.githubbrowser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.ui.model.MainScreenUiModel

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel constructor(
    private val githubApi: GithubApi //todo: make me endpoints
) : ViewModel() {

    private val uiState = MutableLiveData<MainScreenUiModel.UiState>()
    private val userRepositories = MutableLiveData<List<MainScreenUiModel.Repository>>()
    private val user = MutableLiveData<MainScreenUiModel.User>()

    fun getUiState(): LiveData<MainScreenUiModel.UiState> {
        return uiState
    }

    fun getUserRepositories(): LiveData<List<MainScreenUiModel.Repository>> {
        return userRepositories
    }

    fun getUser(): LiveData<MainScreenUiModel.User> {
        return user
    }

    fun searchUser(userId: String) {
        //todo: zip end points
    }


    private fun fetchUserInfo(userId: String) {
        //todo: zip end points
    }

    private fun fetchUserRepos(userId: String) {
        //todo: zip end points
    }

}