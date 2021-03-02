//package com.mvvm_clean.star_wars.features.people_details.presentation.activities
//
//import android.content.Context
//import android.content.Intent
//import com.mvvm_clean.star_wars.core.base.BaseActivity
//import com.mvvm_clean.star_wars.features.people_details.presentation.fragments.PeopleDetailsFragment
//import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
//
///**
// * People detail screen activity.
// */
//class PeopleDetailsActivity : BaseActivity() {
//
//    companion object {
//        private const val INTENT_EXTRA_PARAM_PEOPLE = "INTENT_PARAM_PEOPLE_INFO"
//
//        fun callingIntent(context: Context, speciesEntity: PeopleEntity) =
//            Intent(context, PeopleDetailsActivity::class.java).apply {
//                putExtra(INTENT_EXTRA_PARAM_PEOPLE, speciesEntity)
//            }
//    }
//
//    override fun fragment() =
//        PeopleDetailsFragment.forPeopleInfo(intent.getParcelableExtra(INTENT_EXTRA_PARAM_PEOPLE))
//}
