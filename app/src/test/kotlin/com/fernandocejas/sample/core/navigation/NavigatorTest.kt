package com.fernandocejas.sample.core.navigation

import com.fernandocejas.sample.AndroidTest
import com.fernandocejas.sample.features.login.Authenticator
import com.fernandocejas.sample.features.login.LoginActivity
import com.fernandocejas.sample.features.movies.MoviesActivity
import com.fernandocejas.sample.shouldNavigateTo
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
        RouteActivity::class shouldNavigateTo MoviesActivity::class
    }
}