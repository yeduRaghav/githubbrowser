package com.yrgv.githubbrowser.data.network.endpoints

import com.yrgv.githubbrowser.util.Either
import retrofit2.Call
import retrofit2.Response

/**
 * Parent class from for all endpoints.
 * Handles Api response and exceptions and return localized response for the app
 */
abstract class BaseApiEndpoint<R> {

    protected abstract fun getCall(): Call<R>

    fun execute(): Either<ApiError, R> {
        return try {
            getCall().execute().getLocalizedResponse()
        } catch (throwable: Throwable) {
            throwable.getLocalisedResponse()
        }
    }

    /**
     * Handle successful response from Network
     * */
    private fun Response<R>.getLocalizedResponse(): Either<ApiError, R> {
        return body()?.let {
            Either.value(it)
        } ?: Either.error(ApiError.InvalidResponseBodyError)
    }

    /**
     * Handle exceptions from Network call
     * */
    private fun Throwable.getLocalisedResponse(): Either<ApiError, Nothing> {
        return Either.error(ApiError.getLocalizedErrorResponse(this))
    }

}