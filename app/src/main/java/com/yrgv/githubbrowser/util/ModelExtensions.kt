package com.yrgv.githubbrowser.util

import androidx.annotation.VisibleForTesting
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.data.network.model.Repository
import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.ui.MainScreenUiModel
import com.yrgv.githubbrowser.util.resource.ResourceProvider
import org.joda.time.DateTimeZone
import org.joda.time.Instant

/**
 * Holds extension function useful for various models for their transformation.
 * */

fun User.isResponseInvalid(): Boolean {
    return name.isNullOrBlank() || avatar_url.isNullOrBlank()
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
        lastUpdated = updated_at.serverTimeStampToLocalFormat(resourceProvider),
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


/**
 * @return formatted String that returns date and time in a formatted in original UTC format.
 * */
@VisibleForTesting
fun String.serverTimeStampToLocalFormat(resourceProvider: ResourceProvider): String {
    val dateTime = Instant.parse(this).toDateTime(DateTimeZone.UTC)
    val hourOfDay = dateTime.hourOfDay
    return StringBuilder()
        .append(dateTime.monthOfYear.toDisplayMonth(resourceProvider))
        .append(" ${dateTime.dayOfMonth},")
        .append(" ${dateTime.year}")
        .append(" ${hourOfDay.hourOfDayTo12HourFormat()}")
        .append(":${dateTime.minuteOfHour}")
        .append(":${dateTime.secondOfMinute}")
        .append(" ${dateTime.hourOfDay.halfOfDayToDisplay(resourceProvider)}")
        .toString()
}

@VisibleForTesting
fun Int.halfOfDayToDisplay(resourceProvider: ResourceProvider): String {
    return resourceProvider.getString(this.getHalfOfDayResId()) ?: ""
}

@VisibleForTesting
fun Int.hourOfDayTo12HourFormat(): Int? {
    if (this < 0 || this > 23) return null
    if (this == 0) return 12
    return this.takeIf { this <= 12 } ?: this - 12
}

@VisibleForTesting
fun Int.getHalfOfDayResId(): Int {
    return if (this.isHourOfDayAm()) {
        R.string.half_of_day_am
    } else {
        R.string.half_of_day_pm
    }
}

@VisibleForTesting
fun Int.isHourOfDayAm(): Boolean {
    return this < 12
}

@VisibleForTesting
fun Int.toDisplayMonth(resourceProvider: ResourceProvider): String {
    return getDisplayMonthResId()?.let {
        resourceProvider.getString(it)
    } ?: ""
}

@VisibleForTesting
fun Int.getDisplayMonthResId(): Int? {
    return when (this) {
        1 -> R.string.display_month_january
        2 -> R.string.display_month_february
        3 -> R.string.display_month_march
        4 -> R.string.display_month_april
        5 -> R.string.display_month_may
        6 -> R.string.display_month_june
        7 -> R.string.display_month_july
        8 -> R.string.display_month_august
        9 -> R.string.display_month_september
        10 -> R.string.display_month_october
        11 -> R.string.display_month_november
        12 -> R.string.display_month_december
        else -> null
    }
}

