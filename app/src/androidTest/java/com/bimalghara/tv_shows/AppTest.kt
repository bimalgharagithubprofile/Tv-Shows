package com.bimalghara.tv_shows

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.bimalghara.tv_shows.ui.MainActivity
import com.bimalghara.tv_shows.utils.DataStatus
import com.bimalghara.tv_shows.utils.EspressoIdlingResource
import com.bimalghara.tv_shows.utils.TestUtil.dataStatus
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun displayTvShowsList() {
        dataStatus = DataStatus.Success

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.gridview)).check(matches(isDisplayed()))
    }

    /*@Test
    fun launchTvShowDetailsScreen() {
        dataStatus = DataStatus.Success
        Intents.init()

        onView(withId(R.id.gridview)).check(matches(isDisplayed()))

        onData(anything())
            .inAdapterView(withId(R.id.gridview))
            .atPosition(0)
            .perform(click())

        intended(hasComponent(DetailComposeActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun failToDisplayTvShowsData_error_Network() {
        dataStatus = DataStatus.Fail
        failureType = FailureType.Network

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.gridview)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvErrorMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvErrorMessage)).check(matches(withText("no internet")))
    }

    @Test
    fun failToDisplayTvShowsData_error_Timeout() {
        dataStatus = DataStatus.Fail
        failureType = FailureType.Timeout

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.gridview)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvErrorMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvErrorMessage)).check(matches(withText("socket timeout")))
    }

    @Test
    fun failToDisplayTvShowsData_error_Unauthorized() {
        dataStatus = DataStatus.Fail
        failureType = FailureType.Http

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.gridview)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvErrorMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvErrorMessage)).check(matches(withText("401 - unauthorized")))
    }*/

}
