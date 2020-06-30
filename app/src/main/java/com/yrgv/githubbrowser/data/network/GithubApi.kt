package com.yrgv.githubbrowser.data.network

import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API definition.
 * */
interface GithubApi {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val USER_ENDPOINT = BASE_URL + "users/{userId}"

        private val apiInstance: GithubApi by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().build())
                .build()
                .create(GithubApi::class.java)
        }

        fun getInstance(): GithubApi {
            return apiInstance
        }
    }

    @GET(USER_ENDPOINT)
    fun getUser(
        @Path("userId") userId: String
    ): Call<User>

    @GET("$USER_ENDPOINT/repos")
    fun getUserRepos(
        @Path("userId") userId: String
    ): Call<List<Repository>>

}