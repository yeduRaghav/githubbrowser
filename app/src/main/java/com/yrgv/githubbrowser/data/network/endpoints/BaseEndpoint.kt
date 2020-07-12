package com.yrgv.githubbrowser.data.network.endpoints

import android.util.Log
import com.yrgv.githubbrowser.util.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

/**
 * Parent class for all endpoints.
 * Handles network response and exceptions and return localized response for the app
 */
abstract class BaseEndpoint<R> {

    /**
     * Returns a new Retrofit Call
     * */
    protected abstract fun getCall(): Call<R>

    /**
     * The current call instance
     * */
    private lateinit var currentCall: Call<R>

    /**
     * Invoke to perform the network call.
     *
     * @param cancelOnGoingCall `true`, if there is a call in-progress, attempts to cancel
     * it before the performing the new call. `false`, will not attempt to cancel
     * the previous call in-progress and will perform the new call.
     *
     * @return If network call fails, returns appropriate [EndpointError].
     * If successful, returns Response body <R>
     * */
    suspend fun execute(cancelOnGoingCall: Boolean = true): Either<EndpointError, R> {
        if (cancelOnGoingCall) {
            attemptToCancelCurrentCall()
        }
        currentCall = getCall()
        return performNetworkCall()
    }

    private suspend fun performNetworkCall(): Either<EndpointError, R> {
        return withContext(Dispatchers.IO) {
            try {
                currentCall.execute().getLocalizedResponse()
            } catch (throwable: Throwable) {
                throwable.getLocalisedError()
            }
        }
    }


    private fun attemptToCancelCurrentCall() {
        if (::currentCall.isInitialized) {
            try {
                currentCall.cancel()
            } catch (exception: IllegalStateException) {
                Log.e("BaseEndpoint", exception.message ?: "exception cancelling currentCall")
            }
        }
    }


    /**
     * Handle successful response from Network
     * */
    private fun Response<R>.getLocalizedResponse(): Either<EndpointError, R> {
        return body()?.let {
            Either.value(it)
        } ?: Either.error(EndpointError.InvalidResponseBodyError)
    }

    /**
     * Handle exceptions from Network call
     * */
    private fun Throwable.getLocalisedError(): Either<EndpointError, Nothing> {
        return Either.error(EndpointError.getLocalizedErrorResponse(this))
    }

}