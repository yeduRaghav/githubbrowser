package com.yrgv.githubbrowser.util

import com.yrgv.githubbrowser.data.network.model.User
import com.yrgv.githubbrowser.util.resource.ResourceProvider
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito

class ModelExtensionsTest {

    @Test
    fun testIsNetworkUserResponseInvalid() {
        val userBothFieldsNull = User(null, null)
        Assert.assertTrue(userBothFieldsNull.isResponseInvalid())

        val userBothFieldsEmpty = User("", "")
        Assert.assertTrue(userBothFieldsEmpty.isResponseInvalid())

        val userOnlyNameNull = User(null, "url")
        Assert.assertTrue(userOnlyNameNull.isResponseInvalid())

        val userOnlyNameEmpty = User("", "url")
        Assert.assertTrue(userOnlyNameEmpty.isResponseInvalid())

        val userOnlyUrlNull = User("name", null)
        Assert.assertTrue(userOnlyUrlNull.isResponseInvalid())

        val userOnlyUrlEmpty = User("name", "")
        Assert.assertTrue(userOnlyUrlEmpty.isResponseInvalid())

        val userBothFieldsValid = User("name", "url")
        Assert.assertFalse(userBothFieldsValid.isResponseInvalid())
    }

    @Test
    fun testNetworkUserResponseToUiModel() {
        val networkModelBothFieldsNull = User(null, null)
        networkModelBothFieldsNull.toUiModel().let {
            Assert.assertTrue(it.name.isEmpty() && it.avatarUrl.isEmpty())
        }

        val networkModelBothFieldsEmpty = User("", "")
        networkModelBothFieldsEmpty.toUiModel().let {
            Assert.assertTrue(it.name.isEmpty() && it.avatarUrl.isEmpty())
        }

        val networkModelAvatarNull = User("name", null)
        networkModelAvatarNull.toUiModel().let {
            Assert.assertTrue(it.name == "name" && it.avatarUrl.isEmpty())
        }

        val networkModelAvatarEmpty = User("name", "")
        networkModelAvatarEmpty.toUiModel().let {
            Assert.assertTrue(it.name == "name" && it.avatarUrl.isEmpty())
        }

        val networkModelNameNull = User(null, "avatar")
        networkModelNameNull.toUiModel().let {
            Assert.assertTrue(it.name.isEmpty() && it.avatarUrl == "avatar")
        }

        val networkModelNameEmpty = User("", "avatar")
        networkModelNameEmpty.toUiModel().let {
            Assert.assertTrue(it.name.isEmpty() && it.avatarUrl == "avatar")
        }
    }




    @Test
    fun testServerTimeStampToLocalFormat() {
        val mockValue = "MOCK_VALUE"
        val mockedResourceProvider = Mockito.spy(ResourceProvider::class.java)
        Mockito.`when`(mockedResourceProvider.getString(anyInt())).thenReturn(mockValue)

        val morningFirstHour =
            "1994-09-02T00:00:00Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(morningFirstHour == "$mockValue 2, 1994 12:0:0 $mockValue")

        val midMorning = "1994-09-02T07:45:30Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(midMorning == "$mockValue 2, 1994 7:45:30 $mockValue")

        val perfectNoon =
            "1994-09-02T12:00:00Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(perfectNoon == "$mockValue 2, 1994 12:0:0 $mockValue")

        val bitAfterPerfectNoon =
            "1994-09-02T12:07:05Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(bitAfterPerfectNoon == "$mockValue 2, 1994 12:7:5 $mockValue")

        val afterNoon = "1994-09-02T15:00:00Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(afterNoon == "$mockValue 2, 1994 3:0:0 $mockValue")

        val nightLastHour =
            "1994-09-02T23:00:00Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(nightLastHour == "$mockValue 2, 1994 11:0:0 $mockValue")

        val nightLastSecond =
            "1994-09-02T23:59:59Z".serverTimeStampToLocalFormat(mockedResourceProvider)
        Assert.assertTrue(nightLastSecond == "$mockValue 2, 1994 11:59:59 $mockValue")
    }

    @Test
    fun testHourOfDayTo12HourFormat() {
        Assert.assertTrue((-1).hourOfDayTo12HourFormat() == null)
        Assert.assertTrue(24.hourOfDayTo12HourFormat() == null)
        Assert.assertTrue(25.hourOfDayTo12HourFormat() == null)

        Assert.assertTrue(0.hourOfDayTo12HourFormat() == 12)
        Assert.assertTrue(1.hourOfDayTo12HourFormat() == 1)

        Assert.assertTrue(12.hourOfDayTo12HourFormat() == 12)

        Assert.assertTrue(13.hourOfDayTo12HourFormat() == 1)
        Assert.assertTrue(20.hourOfDayTo12HourFormat() == 8)
        Assert.assertTrue(23.hourOfDayTo12HourFormat() == 11)

    }

    @Test
    fun testIsHourOfDayAm() {
        Assert.assertTrue(0.isHourOfDayAm())

        Assert.assertTrue(1.isHourOfDayAm())
        Assert.assertTrue(11.isHourOfDayAm())

        Assert.assertFalse(12.isHourOfDayAm())
        Assert.assertFalse(13.isHourOfDayAm())
        Assert.assertFalse(24.isHourOfDayAm())
    }
}