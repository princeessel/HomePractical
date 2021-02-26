package com.otis.homepractical

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.otis.homepractical.ui.main.ProListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewFirstItemMoveToDetailsScreenCheckTexts () {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProListAdapter.ViewHolder>(
                0, ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withText("AAA Service Plumbing, Inc."))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Arvada, CO"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testRecyclerViewScrollClickAndMoveToDetailsScreen () {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<ProListAdapter.ViewHolder>(
                9
            )
        ).perform(RecyclerViewActions.actionOnItemAtPosition<ProListAdapter.ViewHolder>(10, ViewActions.click()))

        Espresso.onView(ViewMatchers.withText("Nailed IT Construction Services"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}