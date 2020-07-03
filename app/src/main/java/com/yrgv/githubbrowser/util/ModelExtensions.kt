package com.yrgv.githubbrowser.util

import androidx.annotation.VisibleForTesting
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.ui.MainScreenUiModel
import com.yrgv.githubbrowser.util.resource.ResourceProvider
import org.joda.time.Instant

/**
 * Holds extension function useful for various models for their transformation
 * */

fun User.isResponseInvalid(): Boolean {
    return name.isNullOrBlank() && avatar_url.isNullOrBlank()
}

fun User.toUiModel(): MainScreenUiModel.User {
    return MainScreenUiModel.User(name.orEmpty(), avatar_url.orEmpty())
}

@VisibleForTesting
private fun Repository.toUiModel(resourceProvider: ResourceProvider): MainScreenUiModel.Repository {
    return MainScreenUiModel.Repository(
        id = id,
        name = name,
        description = description,
        lastUpdated = updated_at.toLocalFormat(resourceProvider),
        starsCount = stargazers_count.toString(),
        forksCounts = forks.toString()
    )
}

fun List<Repository>.toUiModels(resourceProvider: ResourceProvider):List<MainScreenUiModel.Repository> {
    return mapTo(arrayListOf()) { it.toUiModel(resourceProvider) }
}

fun MainScreenUiModel.Repository.toBottomSheetData() : RepositoryDetailsBottomSheet.Data {
    return RepositoryDetailsBottomSheet.Data(
        lastUpdated = lastUpdated,
        forksCounts = forksCounts,
        starsCount = starsCount
    )
}

@VisibleForTesting
fun String.toLocalFormat(resourceProvider: ResourceProvider): String {
    val dateTime = Instant.parse(this).toDateTime().toLocalDateTime()

    return StringBuilder()
        .append(dateTime.monthOfYear.toDisplayMonth())
        .append(" ${dateTime.dayOfMonth},")
        .append(" ${dateTime.year}")
        .append(" ${dateTime.hourOfDay.hourOfDayTo12HourFormat()}")
        .append(":${dateTime.minuteOfHour}")
        .append(":${dateTime.secondOfMinute}")
        .append(" ${dateTime.hourOfDay.toHalfOfDay(resourceProvider)}")
        .toString()
}

private fun Int.hourOfDayTo12HourFormat() :Int {
    return takeIf { this<= 12 } ?: this - 12
}

private fun Int.toHalfOfDay(resourceProvider: ResourceProvider): String {
    val resId = if (this.isHourOfDayAm()) {
        R.string.half_of_day_am
    } else {
        R.string.half_of_day_pm
    }
    return resourceProvider.getString(resId).orEmpty()
}

private fun Int.isHourOfDayAm(): Boolean {
    return this < 12
}

private fun Int.toDisplayMonth(): String {
    return when (this) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> ""
    }
}

