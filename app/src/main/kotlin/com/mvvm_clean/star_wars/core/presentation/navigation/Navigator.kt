package com.mvvm_clean.star_wars.core.presentation.navigation

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.mvvm_clean.star_wars.features.login.domain.Authenticator
import com.mvvm_clean.star_wars.features.login.presentation.activities.LoginActivity
import com.mvvm_clean.star_wars.features.people_details.presentation.activities.PeopleDetailsActivity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.activities.PeopleListActivity
import javax.inject.Inject
import javax.inject.Singleton


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
        activity: FragmentActivity,
        peopleEntity: PeopleEntity,
        navigationExtras: Extras
    ) {
        val intent = PeopleDetailsActivity.callingIntent(activity, peopleEntity)
//        val sharedView = navigationExtras.transitionSharedElement as TextView
//        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity,
//                    sharedView,
//                    sharedView.transitionName
//                )

        activity.startActivity(intent)
    }

    class Extras(val transitionSharedElement: View)
}


