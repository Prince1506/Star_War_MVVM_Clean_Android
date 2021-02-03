package com.mvvm_clean.star_wars.core.navigation

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.mvvm_clean.star_wars.features.characters.PeopleDetailsActivity
import com.mvvm_clean.star_wars.features.characters.PeopleListActivity
import com.mvvm_clean.star_wars.features.characters.ResultEntity
import com.mvvm_clean.star_wars.features.login.Authenticator
import com.mvvm_clean.star_wars.features.login.LoginActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

    fun navigateToScreens(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showPeopleList(context)
            false -> showLogin(context)
        }
    }

    private fun showPeopleList(context: Context) = context.startActivity(PeopleListActivity.callingIntent(context))

    fun showPeopleDetails(activity: FragmentActivity, movie: ResultEntity, navigationExtras: Extras) {
        val intent = PeopleDetailsActivity.callingIntent(activity, movie)
        val sharedView = navigationExtras.transitionSharedElement as TextView
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    sharedView,
                    sharedView.transitionName
                )

        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}


