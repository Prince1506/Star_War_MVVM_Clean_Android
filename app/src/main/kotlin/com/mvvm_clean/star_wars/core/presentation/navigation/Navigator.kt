package com.mvvm_clean.star_wars.core.presentation.navigation

import android.content.Context
import android.os.Bundle
import com.mvvm_clean.star_wars.core.constants.KeyConstants.INTENT_EXTRA_PARAM_PEOPLE
import com.mvvm_clean.star_wars.features.login.domain.Authenticator
import com.mvvm_clean.star_wars.features.login.presentation.activities.LoginActivity
import com.mvvm_clean.star_wars.features.people_details.presentation.fragments.PeopleDetailsFragment
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.activities.PeopleListActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * To navigate from screen 1 to screen 2 we write logic here
 */

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) = context.startActivity(
        LoginActivity.callingIntent(
            context
        )
    )

    fun navigateToScreens(context: Context) {

        when (authenticator.userLoggedIn()) {

            true -> showPeopleList(context)
            false -> showLogin(context)
        }
    }

    private fun showPeopleList(context: Context) =
        context.startActivity(PeopleListActivity.callingIntent(context))

    fun showPeopleDetails(
        activity: PeopleListActivity,
        peopleEntity: PeopleEntity
    ) {
        val bundle = Bundle().apply {
            putParcelable(INTENT_EXTRA_PARAM_PEOPLE, peopleEntity)
        }
        activity.replaceFragment(PeopleDetailsFragment(), bundle, true)
    }

    class Extras
}


