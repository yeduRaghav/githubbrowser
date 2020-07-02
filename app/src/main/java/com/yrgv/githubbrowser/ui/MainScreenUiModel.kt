package com.yrgv.githubbrowser.ui

/**
 * Defines Ui models for main screens
 */
class MainScreenUiModel {

    enum class UiState {
        LOADING, ERROR, LOADED
    }

    data class User(
        val name: String,
        val avatarUrl: String
    )

    data class Repository(
        val id: Long,
        val name: String,
        val description: String?,
        val lastUpdated: String,
        val starsCount: String,
        val forksCounts: String
    )
}