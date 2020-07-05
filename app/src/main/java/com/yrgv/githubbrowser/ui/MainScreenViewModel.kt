package com.yrgv.githubbrowser.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.data.network.endpoints.ApiError
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.util.Either
import com.yrgv.githubbrowser.util.isResponseInvalid
import com.yrgv.githubbrowser.util.resource.ResourceProvider
import com.yrgv.githubbrowser.util.toUiModel
import com.yrgv.githubbrowser.util.toUiModels
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel constructor(
    private val resourceProvider: ResourceProvider,
    private val githubApi: GithubApi
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

    private fun handleApiResult(userFromApi: User, repositoriesFromApi: List<Repository>) {
        if (userFromApi.isResponseInvalid()) {
            return uiState.postValue(MainScreenUiModel.UiState.ERROR)
        }
        user.postValue(userFromApi.toUiModel())
        userRepositories.postValue(repositoriesFromApi.toUiModels(resourceProvider))
        uiState.postValue(MainScreenUiModel.UiState.LOADED)
    }

    @SuppressLint("CheckResult")
    fun searchUser(userId: String) {
        uiState.postValue(MainScreenUiModel.UiState.LOADING)
        Single.zip(
            githubApi.getUser(userId).subscribeOn(Schedulers.io()),
            githubApi.getUserRepos(userId).subscribeOn(Schedulers.io()),
            BiFunction<User, List<Repository>, Either<ApiError, Pair<User, List<Repository>>>> { user, repos ->
                Either.value(Pair(user, repos))
            }
        ).onErrorReturn {
            Either.error(ApiError.getLocalizedErrorResponse(it))
        }.subscribe { response ->
            handleApiResponse(response)
        }
    }

    private fun handleApiResponse(response: Either<ApiError, Pair<User, List<Repository>>>) {
        when (response) {
            is Either.Error -> {
                uiState.postValue(MainScreenUiModel.UiState.ERROR)
            }
            is Either.Value -> {
                handleApiResult(response.value.first, response.value.second)
            }
        }
    }

}