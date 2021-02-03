package com.mvvm_clean.star_wars.core.navigation

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.features.characters.PeopleListActivity
import com.mvvm_clean.star_wars.features.login.Authenticator
import com.mvvm_clean.star_wars.features.login.LoginActivity
import com.mvvm_clean.star_wars.shouldNavigateTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @MockK
    private lateinit var authenticator: Authenticator

    @Before
    fun setup() {
        navigator = Navigator(authenticator)
    }

    @Test
    fun `should forward user to login screen`() {
        every { authenticator.userLoggedIn() } returns false

        navigator.showMain(context())

        verify(exactly = 1) { authenticator.userLoggedIn() }
        RouteActivity::class shouldNavigateTo LoginActivity::class
    }

    @Test
    fun `should forward user to movies screen`() {
        every { authenticator.userLoggedIn() } returns true

        navigator.showMain(context())

        verify(exactly = 1) { authenticator.userLoggedIn() }
        RouteActivity::class shouldNavigateTo PeopleListActivity::class
    }
}