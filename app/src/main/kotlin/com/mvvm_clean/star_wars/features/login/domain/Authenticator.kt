package com.mvvm_clean.star_wars.features.login.domain

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Although this app doesn't has login screen, but mostly all production
 * application has. So this logic is included inside the architecture for
 * unit testing purpose if user needs to authenticate.
 */
@Singleton
class Authenticator
@Inject constructor() {

    /*
    Learning purpose: We assume the user is always logged in
    Here you should put your own logic to return whether the user
    is authenticated or not
    */
    fun userLoggedIn() = true
}
