package com.mvvm_clean.user_details.features.people_list.presentation.ui.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.user_details.core.base.BaseActivity
import com.mvvm_clean.user_details.features.people_list.presentation.ui.fragments.PeopleListFragment

/**
 * Activity responsible to show people list on screen.
 */
class PeopleListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, PeopleListActivity::class.java)
    }

    override fun fragment() = PeopleListFragment()
}
