package com.yrgv.githubbrowser.data.network.endpoints

import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.data.network.model.Repository
import retrofit2.Call

class GetUserReposEndpoint(private val githubApi: GithubApi) : BaseApiEndpoint<List<Repository>>() {

    private lateinit var userId: String

    fun setData(userId: String) {
        this.userId = userId
    }

    override fun getCall(): Call<List<Repository>> {
        return githubApi.getUserRepos(userId)
    }
}