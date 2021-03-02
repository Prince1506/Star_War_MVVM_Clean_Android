package com.mvvm_clean.star_wars.features.people_details.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.base.BaseFragment
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.close
import com.mvvm_clean.star_wars.core.domain.extension.failure
import com.mvvm_clean.star_wars.core.domain.extension.observe
import com.mvvm_clean.star_wars.core.domain.extension.viewModel
import com.mvvm_clean.star_wars.databinding.FragmentPeopleDetailsBinding
import com.mvvm_clean.star_wars.features.common.domain.models.ApiFailure
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity


/**
 * Fragment screen for people detail data.
 */
class PeopleDetailsFragment : BaseFragment() {

    // Static-------------------------------------
    companion object {
        const val PARAM_PEOPLE_ENTITY = "param_peopleEntity"

        fun forPeopleInfo(speciesEntity: PeopleEntity?): PeopleDetailsFragment {

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
        mPeopleDetailsViewModel = viewModel(viewModelFactory) {
            observe(getPeopleDetailMediatorLiveData(), ::renderPeopleDetails)
            failure(getFailureLiveData(), ::handleApiFailure)
        }
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
        val peopleEntity = (arguments?.get(PARAM_PEOPLE_ENTITY) as PeopleEntity)
        mPeopleDetailsViewModel.updatePeopleDetailWithPeopleInfo(peopleEntity)
        showProgress()
        mPeopleDetailsViewModel.setPeopleEntityObserver(peopleEntity)
    }


    //  Helper Methods-----------------------------------------
    private fun renderPeopleDetails(peopleDetailsDataModel: PeopleDetailsDataModel?) {

        hideProgress()
        peopleDetailsBinding.peopleDetailsDataModel = peopleDetailsDataModel
    }


    private fun handleApiFailure(failure: Failure?) {

        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is ApiFailure.NonExistentPeopleList -> {
                notify(R.string.failure_people_non_existent); close()
            }
            else -> {
                notify(R.string.failure_server_error); close()
            }
        }
    }
}
