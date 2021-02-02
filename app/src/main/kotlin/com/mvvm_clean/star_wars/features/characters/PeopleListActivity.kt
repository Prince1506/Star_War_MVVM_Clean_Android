package com.mvvm_clean.star_wars.features.characters

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.platform.BaseActivity

class PeopleListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, PeopleListActivity::class.java)
    }

    override fun fragment() = PeopleListFragment()
}
