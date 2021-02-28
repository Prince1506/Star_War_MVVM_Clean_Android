package com.mvvm_clean.star_wars.features.people_list.presentation.ui.fragments

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.activities.PeopleListActivity
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.registers.CountingIdlingResourceSingleton
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PeopleListFragmentTest {

    // region constants  ----------------------------------------------
    val mPeopleName = "a"

    @get:Rule
    val activityTestRule = ActivityTestRule(PeopleListActivity::class.java)

    // Region helper fields  ----------------------------------------------
    @Before
    fun showListFragment() {
        val beginTransaction = activityTestRule.activity.supportFragmentManager.beginTransaction()
        val peopleListFragment = PeopleListFragment()
        beginTransaction.add(peopleListFragment, PeopleListFragment::class.java.simpleName)
        beginTransaction.commit()
    }
    // End helper fields ----------------------------------------------


    @Before
    fun setup() {
        IdlingRegistry.getInstance()
            .register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance()
            .unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Before
    fun registerIdlingResource() {

    }

    @After
    fun unregisterIdlingResource() {

    }

    // Region helper methods ----------------------------------------------
    @Test
    fun checkUserInputIntoSearchField() {
        // Assert

        // Act
        onView(withId(R.id.tv_peopleList_searchField)).perform(typeText(mPeopleName))
        onView(withId(R.id.tv_peopleList_searchField)).perform(pressImeActionButton())
        Espresso.closeSoftKeyboard()

        // Verify
        onView(withId(R.id.tv_peopleList_searchField)).check(matches(withText(mPeopleName)))

    }

    @Test
    fun checkIfRecyclerViewIsClickable() {
        // Act
        onView(withId(R.id.tv_peopleList_searchField)).perform(typeText(mPeopleName))
        onView(withId(R.id.tv_peopleList_searchField)).perform(pressImeActionButton())
        Espresso.closeSoftKeyboard()

        // Verify
        if (getRVcount() > 0) {
            onView(withId(R.id.rv_people_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        }
    }

    private fun getRVcount(): Int {
        val recyclerView =
            activityTestRule.activity.findViewById(R.id.rv_people_list) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
    // Region helper classes ----------------------------------------------


    // End region classes ----------------------------------------------


    // End region helper methods ----------------------------------------------
}