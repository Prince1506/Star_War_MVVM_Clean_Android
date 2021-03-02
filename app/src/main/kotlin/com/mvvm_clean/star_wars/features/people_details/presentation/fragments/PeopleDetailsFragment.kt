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
import com.mvvm_clean.star_wars.core.domain.extension.*
import com.mvvm_clean.star_wars.databinding.FragmentPeopleDetailsBinding
import com.mvvm_clean.star_wars.features.common.domain.models.ApiFailure
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity


private const val FILM_URL = "http://swapi.dev/api/films/"
private const val SPECIES_URL = "http://swapi.dev/api/species/"
private const val PLANET_URL = "http://swapi.dev/api/planets/"

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
            observe(mPeopleDetailMediatorLiveData, ::renderPeopleDetails)
            failure(failureLiveData, ::handleApiFailure)
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

        makeApiCalls(peopleEntity)

    }


    //  Helper Methods-----------------------------------------

    private fun makeApiCalls(peopleEntity: PeopleEntity) {
        peopleEntity.homeworld?.let { it ->
            getIdFromUrl(it).apply {
                if (this != -1)
                    mPeopleDetailsViewModel.loadPlanetData(this)
            }
        }


        peopleEntity.let {
            for (film in it.films!!) {
                getIdFromUrl(film).apply {
                    if (this != -1)
                        mPeopleDetailsViewModel.loadFilmData(this)
                }

            }
            for (species in it.species!!) {
                getIdFromUrl(species).apply {
                    if (this != -1) mPeopleDetailsViewModel.loadSpeciesData(this)
                }
            }
        }
    }

    private fun getIdFromUrl(url: String): Int {

        val urlArrWithId =
            if (url.contains(getString(R.string.peopleDetails_urlPart_film))) {
                url.split(FILM_URL).toTypedArray()
            } else if (url.contains(getString(R.string.peopleDetails_urlPart_species))) {
                url.split(SPECIES_URL).toTypedArray()
            } else if (url.contains(getString(R.string.peopleDetails_urlPart_planets))) {
                url.split(PLANET_URL).toTypedArray()
            } else {
                arrayOf(String.empty(), String.empty())
            }


        var urlId = -1 // -1 tells that id is not available

        if (urlArrWithId.size > 1 && urlArrWithId[1].contains(getString(R.string.forward_slash))) {
            val filmIdStr = urlArrWithId[1].split(getString(R.string.forward_slash))[0]
            try {
                urlId = filmIdStr.toInt()
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
        }
        return urlId
    }


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
        }
    }

}
