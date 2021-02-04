package com.mvvm_clean.star_wars.features.people_list.presentation.ui.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.base.BaseActivity
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.fragments.PeopleListFragment

class PeopleListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, PeopleListActivity::class.java)
    }

    override fun fragment() = PeopleListFragment()
}
