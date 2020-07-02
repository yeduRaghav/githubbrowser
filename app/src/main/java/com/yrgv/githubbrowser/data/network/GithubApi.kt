package com.yrgv.githubbrowser.data.network

import android.util.Log
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.v("Guruve", message)
                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
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
    ): Single<User>

    @GET("$USER_ENDPOINT/repos")
    fun getUserRepos(
        @Path("userId") userId: String
    ): Single<List<Repository>>

}