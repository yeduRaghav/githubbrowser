package com.yrgv.githubbrowser.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Describes a github user
 */
data class User(
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatar_url: String
)