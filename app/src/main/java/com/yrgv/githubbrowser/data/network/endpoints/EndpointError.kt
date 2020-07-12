package com.yrgv.githubbrowser.data.network.endpoints

import java.net.UnknownHostException

/**
 * Defines localized error responses and their associated data.
 */
sealed class EndpointError(open val message: String?) {
    object InvalidResponseBodyError : EndpointError(null)
    data class NetworkError(override val message: String?) : EndpointError(message)
    data class UnhandledError(override val message: String?, val originalException: Throwable?) :
        EndpointError(message)
    //add any other errors the app would like to respond to.

    companion object {

        fun getLocalizedErrorResponse(exception: Throwable? = null): EndpointError {
            return when (exception) {
                is UnknownHostException -> NetworkError(exception.message)
                else -> getUnhandledError(exception)
            }
        }

        private fun getUnhandledError(exception: Throwable?): UnhandledError {
            return exception?.let {
                UnhandledError(exception.message, it)
            } ?: UnhandledError(
                "Unknown exception, see EndpointError.getLocalizedErrorResponse()",
                null
            )
        }

    }
}