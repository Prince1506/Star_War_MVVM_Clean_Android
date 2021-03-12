package com.mvvm_clean.user_details.features.people_details.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mvvm_clean.user_details.R
import com.mvvm_clean.user_details.core.base.BaseFragment
import com.mvvm_clean.user_details.databinding.FragmentPeopleDetailsBinding
import com.mvvm_clean.user_details.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.user_details.features.people_list.data.repo.response.Datum


/**
 * Fragment screen for people detail data.
 */
class PeopleDetailsFragment : BaseFragment() {

    // Static-------------------------------------
    companion object {
        const val PARAM_PEOPLE_ENTITY = "param_peopleEntity"

        fun forPeopleInfo(speciesEntity: Datum?): PeopleDetailsFragment {

            val peopleDetailsFragment = PeopleDetailsFragment()
            speciesEntity?.let {
                val arguments = Bundle()
                arguments.putParcelable(PARAM_PEOPLE_ENTITY, it)
                peopleDetailsFragment.arguments = arguments
            }
            return peopleDetailsFragment
        }
    }


    //  Late in variables----------------------------------------------
    private lateinit var mPeopleDetailsViewModel: PeopleDetailsViewModel
    private lateinit var peopleDetailsBinding: FragmentPeopleDetailsBinding

    //  Override Methods--------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }


    override fun layoutId() = R.layout.fragment_people_details


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        peopleDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_people_details, container, false)
                    as FragmentPeopleDetailsBinding

        return peopleDetailsBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val datum = (arguments?.get(PARAM_PEOPLE_ENTITY) as Datum)
        datum.setFullName()
        peopleDetailsBinding.datumDataModel = datum
    }


    //  Helper Methods-----------------------------------------
}
