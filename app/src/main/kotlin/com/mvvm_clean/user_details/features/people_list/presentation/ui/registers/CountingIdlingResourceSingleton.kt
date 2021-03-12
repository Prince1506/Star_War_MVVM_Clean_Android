package com.mvvm_clean.user_details.features.people_list.presentation.ui.registers

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * This class is used by espresso to wait for API to finish.
 */
object CountingIdlingResourceSingleton {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = countingIdlingResource.increment()

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}