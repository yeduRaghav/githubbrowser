package com.yrgv.githubbrowser.data.network.endpoints

import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.data.network.model.User
import retrofit2.Call


class GetUserInfoEndpoint(private val githubApi: GithubApi) : BaseApiEndpoint<User>() {

    private lateinit var userId: String

    fun setData(userId: String) {
        this.userId = userId
    }

    override fun getCall(): Call<User> {
        return githubApi.getUser(userId)
    }

}

