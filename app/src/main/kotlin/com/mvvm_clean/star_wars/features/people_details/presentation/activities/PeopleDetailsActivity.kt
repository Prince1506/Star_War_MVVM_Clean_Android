package com.mvvm_clean.star_wars.features.people_details.presentation.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.base.BaseActivity
import com.mvvm_clean.star_wars.features.people_details.presentation.fragments.PeopleDetailsFragment
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.SpeciesListEntity

class PeopleDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_PEOPLE = "INTENT_PARAM_PEOPLE_INFO"

        fun callingIntent(context: Context, speciesListEntity: SpeciesListEntity): Intent {
            val intent = Intent(context, PeopleDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_PEOPLE, speciesListEntity)
            return intent
        }
    }

    override fun fragment() =
        PeopleDetailsFragment.forPeopleInfo(intent.getParcelableExtra(INTENT_EXTRA_PARAM_PEOPLE))
}
