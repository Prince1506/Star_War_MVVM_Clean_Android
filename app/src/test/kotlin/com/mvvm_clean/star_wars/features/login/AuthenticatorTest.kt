package com.mvvm_clean.star_wars.features.login

import com.mvvm_clean.star_wars.UnitTest
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test
    fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }
}
