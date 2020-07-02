package com.yrgv.githubbrowser.util

import androidx.annotation.VisibleForTesting
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.ui.MainScreenUiModel

@VisibleForTesting
private fun Repository.toUiModel(): MainScreenUiModel.Repository {
    return MainScreenUiModel.Repository(
        id = id,
        name = name,
        description = description,
        lastUpdated = updated_at.toLocalFormat(),
        starsCount = stargazers_count.toString(),
        forksCounts = forks.toString()
    )
}

fun List<Repository>.toUiModels():List<MainScreenUiModel.Repository> {
    return mapTo(arrayListOf()) { it.toUiModel() }
}

fun MainScreenUiModel.Repository.toBottomSheetData() : RepositoryDetailsBottomSheet.Data {
    return RepositoryDetailsBottomSheet.Data(
        lastUpdated = lastUpdated,
        forksCounts = forksCounts,
        starsCount = starsCount
    )
}

@VisibleForTesting
private fun String.toLocalFormat(): String {
    return "LOCAL DATE " //todo:
}