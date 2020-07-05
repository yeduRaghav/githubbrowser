package com.yrgv.githubbrowser.util

import com.yrgv.githubbrowser.R
import org.junit.Assert
import org.junit.Test

/**
 * Instrumentation tests for ModelExtensions
 */
class ModelExtensionsKtTest {

    @Test
    fun testGetHalfOfDayResId() {
        Assert.assertTrue((-1).getHalfOfDayResId() == R.string.half_of_day_am)
        Assert.assertTrue(0.getHalfOfDayResId() == R.string.half_of_day_am)
        Assert.assertTrue(1.getHalfOfDayResId() == R.string.half_of_day_am)
        Assert.assertTrue(11.getHalfOfDayResId() == R.string.half_of_day_am)
        Assert.assertTrue(12.getHalfOfDayResId() == R.string.half_of_day_pm)
        Assert.assertTrue(13.getHalfOfDayResId() == R.string.half_of_day_pm)
        Assert.assertTrue(24.getHalfOfDayResId() == R.string.half_of_day_pm)
        Assert.assertTrue(25.getHalfOfDayResId() == R.string.half_of_day_pm)
    }

    @Test
    fun testGetDisplayMonthResId() {
        Assert.assertTrue(1.getDisplayMonthResId() == R.string.display_month_january)
        Assert.assertTrue(2.getDisplayMonthResId() == R.string.display_month_february)
        Assert.assertTrue(3.getDisplayMonthResId() == R.string.display_month_march)
        Assert.assertTrue(4.getDisplayMonthResId() == R.string.display_month_april)
        Assert.assertTrue(5.getDisplayMonthResId() == R.string.display_month_may)
        Assert.assertTrue(6.getDisplayMonthResId() == R.string.display_month_june)
        Assert.assertTrue(7.getDisplayMonthResId() == R.string.display_month_july)
        Assert.assertTrue(8.getDisplayMonthResId() == R.string.display_month_august)
        Assert.assertTrue(9.getDisplayMonthResId() == R.string.display_month_september)
        Assert.assertTrue(10.getDisplayMonthResId() == R.string.display_month_october)
        Assert.assertTrue(11.getDisplayMonthResId() == R.string.display_month_november)
        Assert.assertTrue(12.getDisplayMonthResId() == R.string.display_month_december)

        Assert.assertTrue(0.getDisplayMonthResId() == null)
        Assert.assertTrue((-1).getDisplayMonthResId() == null)
        Assert.assertTrue(13.getDisplayMonthResId() == null)
        Assert.assertTrue(14.getDisplayMonthResId() == null)
    }

}