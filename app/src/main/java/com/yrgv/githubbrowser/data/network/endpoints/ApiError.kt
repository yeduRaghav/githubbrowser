package com.yrgv.githubbrowser.data.network.endpoints

import java.net.UnknownHostException

/**
 * Defines localized error responses and their associated data.
 */
sealed class ApiError {
    data class GenericError(val errorBody: String?) : ApiError()
    data class NetworkError(val message: String?) : ApiError()

    //add any other errors that the app would like to respond to
    companion object {

        fun getLocalizedErrorResponse(exception: Throwable? = null): ApiError {
            return when (exception) {
                is UnknownHostException -> NetworkError(exception.message)
                else -> GenericError(exception?.message)
            }
        }
    }
}