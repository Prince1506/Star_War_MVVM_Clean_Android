package com.mvvm_clean.star_wars.core.presentation.navigation

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.features.login.domain.Authenticator
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.activities.PeopleListActivity
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import shouldNavigateTo

class NavigatorTest : AndroidTest() {
    // Late int Variables -----------------
    private lateinit var navigator: Navigator

    // Annotations Variables -----------------
    @MockK
    private lateinit var authenticator: Authenticator

    // Override Methods--------------------------------------
    @Before
    fun setup() {
        navigator = Navigator(authenticator)
    }

    // Test Cases---------------------------------------------
    @Test
    fun `should forward user to people list screen`() {
        every { authenticator.userLoggedIn() } returns true

        navigator.navigateToScreens(context())
        verify(exactly = 1) { authenticator.userLoggedIn() }
        RouteActivity::class shouldNavigateTo PeopleListActivity::class
    }
}