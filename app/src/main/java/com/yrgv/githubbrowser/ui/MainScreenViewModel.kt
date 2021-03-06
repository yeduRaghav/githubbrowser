package com.yrgv.githubbrowser.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yrgv.githubbrowser.data.network.endpoints.EndpointError
import com.yrgv.githubbrowser.data.network.endpoints.GetUserInfoEndpoint
import com.yrgv.githubbrowser.data.network.endpoints.GetUserReposEndpoint
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.util.Either
import com.yrgv.githubbrowser.util.isResponseInvalid
import com.yrgv.githubbrowser.util.resource.ResourceProvider
import com.yrgv.githubbrowser.util.toUiModel
import com.yrgv.githubbrowser.util.toUiModels
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel constructor(
    private val resourceProvider: ResourceProvider,
    private val userInfoEndpoint: GetUserInfoEndpoint,
    private val userReposEndpoint: GetUserReposEndpoint
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
        uiState.postValue(MainScreenUiModel.UiState.LOADING)
        fetchUserDataFromApi(userId)
    }

    private fun fetchUserDataFromApi(userId: String) {
        viewModelScope.launch {
            val userInfo = async { fetchUserInfoFromApi(userId) }
            val userRepos = async { fetchUserReposFromApi(userId) }
            handleUserDataApiResponse(userInfo.await(), userRepos.await())
        }
    }

    private fun handleUserDataApiResponse(
        userInfo: Either<EndpointError, User>,
        userRepos: Either<EndpointError, List<Repository>>
    ) {
        if (userInfo.isError() || userRepos.isError()) {
            uiState.postValue(MainScreenUiModel.UiState.ERROR)
            return
        }
        if (userInfo is Either.Value && userRepos is Either.Value) {
            handleUserDataApiResponseSuccess(userInfo.value, userRepos.value)
        }
    }

    private fun handleUserDataApiResponseSuccess(userInfo: User, userRepos: List<Repository>) {
        if (userInfo.isResponseInvalid()) {
            uiState.postValue(MainScreenUiModel.UiState.ERROR)
            return
        }
        viewModelScope.launch {
            userRepositories.postValue(userRepos.toUiModels(resourceProvider))
            user.postValue(userInfo.toUiModel())
            uiState.postValue(MainScreenUiModel.UiState.LOADED)
        }
    }

    private suspend fun fetchUserInfoFromApi(userId: String): Either<EndpointError, User> {
        return userInfoEndpoint.apply { setData(userId) }.execute()
    }

    private suspend fun fetchUserReposFromApi(userId: String): Either<EndpointError, List<Repository>> {
        return userReposEndpoint.apply { setData(userId) }.execute()
    }

}