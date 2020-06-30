package com.yrgv.githubbrowser.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Describes a github repository
 */
data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("stargazers_count") val stargazers_count: Int,
    @SerializedName("forks") val forks: Int
)