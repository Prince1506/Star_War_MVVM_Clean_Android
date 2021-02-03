package com.mvvm_clean.star_wars.features.characters

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.platform.BaseActivity

class PeopleDetailsActivity : BaseActivity() {

    companion object {
        private const val  INTENT_EXTRA_PARAM_PEOPLE= "com.mvvm_clean.star_war.INTENT_PARAM_PEOPLE_INFO"

        fun callingIntent(context: Context, movie: ResultEntity): Intent {
            val intent = Intent(context, PeopleDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_PEOPLE, movie)
            return intent
        }
    }

    override fun fragment() =
        PeopleDetailsFragment.forPeopleInfo(intent.getParcelableExtra(INTENT_EXTRA_PARAM_PEOPLE))
}
