package com.yrgv.githubbrowser.data.network.endpoints

import java.net.UnknownHostException

/**
 * Defines localized error responses and their associated data.
 */
sealed class ApiError(open val message: String?) {
    object InvalidResponseBodyError : ApiError(null)
    data class NetworkError(override val message: String?) : ApiError(message)
    data class UnhandledError(override val message: String?, val originalException: Throwable?) :
        ApiError(message)
    //add any other errors the app would like to respond to.

    companion object {

        fun getLocalizedErrorResponse(exception: Throwable? = null): ApiError {
            return when (exception) {
                is UnknownHostException -> NetworkError(exception.message)
                else -> getUnhandledError(exception)
            }
        }

        private fun getUnhandledError(exception: Throwable?): UnhandledError {
            return exception?.let {
                UnhandledError(exception.message, it)
            } ?: UnhandledError("Unknown exception, see ApiError.getLocalizedErrorResponse()", null)
        }

    }
}