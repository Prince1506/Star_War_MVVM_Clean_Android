package com.mvvm_clean.user_details.features.people_list.presentation.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mvvm_clean.user_details.R
import com.mvvm_clean.user_details.features.people_list.presentation.ui.activities.PeopleListActivity
import com.mvvm_clean.user_details.features.people_list.presentation.ui.registers.CountingIdlingResourceSingleton
import kotlinx.android.synthetic.main.fragment_people_list.*
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val PEOPLE_NAME_EXISTED = "a"
const val PEOPLE_NAME_NON_EXISTED = "ewdfsdfsdfqwef"

class PeopleListFragmentTest {

    // Field Variables  ----------------------------------------------
    @get:Rule
    val activityTestRule = ActivityTestRule(PeopleListActivity::class.java)

    // Overridden Methods  ----------------------------------------------
    @Before
    fun showListFragment() {
        val beginTransaction = activityTestRule.activity.supportFragmentManager.beginTransaction()
        val peopleListFragment = PeopleListFragment()
        beginTransaction.add(peopleListFragment, PeopleListFragment::class.java.simpleName)
        beginTransaction.commit()
    }

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


    // Test Cases ----------------------------------------------
    /**
     *  Please make sure network is available for this test inside device / emulator
     *
     * Check that on click of search button:
     * 1. people list comes up with search result
     * 2. On click of item inside search list, detail screen comes up.
     * 3. Inside detail screen all data are valid
     */
    @Test
    fun searchPeopleAndShowDetailScreen() {

        // Act
        onView(withId(R.id.tv_peopleList_searchField)).perform(typeText(PEOPLE_NAME_EXISTED))
        onView(withId(R.id.tv_peopleList_searchField)).perform(pressImeActionButton())
        Espresso.closeSoftKeyboard()

        // Verify
        if (getRVcount()!! > 0) {
            onView(withId(R.id.rv_people_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        }
        if (isConnected(activityTestRule.activity)) {
            onView(withId(R.id.cl_peopleDetail_parrent)).check(matches(isDisplayed()))

            // Verifying all data fields inside detail screen
            onView(withId(R.id.tv_peopleDetails_name)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_birth_year)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_height)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_speciesName)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_language)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_homeworld)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_population)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_films)).check(matches(not(withText(""))))
            onView(withId(R.id.tv_peopleDetails_opening_crawl)).check(matches(not(withText(""))))

        }
    }

    /**
     *  Please make sure network is available for this test inside device / emulator
     *
     * Check that when no name is searched inside People list API
     *  1. People list screen comes up
     *  2. Error message is shown to user that no list found
     */
    @Test
    fun searchPeopleAndGetNoList() {

        // Act
        onView(withId(R.id.tv_peopleList_searchField)).perform(typeText(PEOPLE_NAME_NON_EXISTED))
        onView(withId(R.id.tv_peopleList_searchField)).perform(pressImeActionButton())
        Espresso.closeSoftKeyboard()

        // Verify
        if (getRVcount()!! > 0) {
            onView(withId(R.id.rv_people_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        }
        if (isConnected(activityTestRule.activity)) {
            onView(withId(R.id.cl_peopleList_parent)).check(matches(isDisplayed()))
            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.empty_list)))
        }
    }

    /**
     *  Please make sure network is un-available for this test inside device / emulator
     *
     * Check that on click of search button:
     *  1. People list screen stays.
     *  2. Error message is shown that no network is available to user
     *
     */
    @Test
    fun searchPeopleAndShowNoNetworkMessage() {

        // Act
        onView(withId(R.id.tv_peopleList_searchField)).perform(typeText(PEOPLE_NAME_EXISTED))
        onView(withId(R.id.tv_peopleList_searchField)).perform(pressImeActionButton())
        Espresso.closeSoftKeyboard()

        // Verify
        if (getRVcount()!! > 0) {
            onView(withId(R.id.rv_people_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        }
        if (!isConnected(activityTestRule.activity)) {
            onView(withId(R.id.cl_peopleList_parent)).check(matches(isDisplayed()))

            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.failure_network_connection)))
        }
    }


    // Helper Methods--------------------------------------------------------------------
    private fun getRVcount(): Int? {
        val recyclerView = activityTestRule.activity.rv_people_list as RecyclerView
        return recyclerView.adapter?.itemCount
    }

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}