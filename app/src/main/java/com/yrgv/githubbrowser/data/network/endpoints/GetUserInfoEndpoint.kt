package com.yrgv.githubbrowser.data.network.endpoints

import android.annotation.SuppressLint
import com.yrgv.githubbrowser.data.network.GithubApi
import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.util.Either
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class GetUserInfoEndpoint(private val githubApi: GithubApi) {

//    @SuppressLint("CheckResult")
//    fun execute(userId: String): Single<Either<ApiError, User?>> {
//
//        return Single.just(call.execute())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .flatMap {
//                Single.just(Either.value(it.body()))
//            }
 //   }



}