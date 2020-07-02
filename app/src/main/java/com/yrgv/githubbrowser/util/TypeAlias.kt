package com.yrgv.githubbrowser.util

import com.yrgv.githubbrowser.ui.MainScreenUiModel

/**
 * Defines Aliases for convenience
 */

typealias SimpleCallback = () -> Unit

typealias SearchClickListener = (query: String) -> Unit

typealias RepositoryItemClickListener = (repository: MainScreenUiModel.Repository) -> Unit
