package com.yrgv.githubbrowser.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.util.toUiModels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    @SuppressLint("CheckResult")
    fun searchUser(userId: String) {
        githubApi.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s, e ->
                //todo: handle error and exception
                uiState.postValue(MainScreenUiModel.UiState.LOADED)
                user.postValue(MainScreenUiModel.User(s.name, s.avatar_url))
            }

        githubApi.getUserRepos(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s, e ->
                //todo: handle error and exception
                uiState.postValue(MainScreenUiModel.UiState.LOADED)
                userRepositories.postValue(s.toUiModels())
            }
    }

    private fun fetchUserInfo(userId: String) {
        //todo: zip end points
    }

    private fun fetchUserRepos(userId: String) {
        //todo: zip end points
    }

}