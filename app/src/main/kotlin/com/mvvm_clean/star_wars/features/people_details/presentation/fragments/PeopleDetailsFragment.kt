package com.mvvm_clean.star_wars.features.people_details.presentation.fragments

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.base.BaseFragment
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.*
import com.mvvm_clean.star_wars.features.common.domain.models.ApiFailure
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import kotlinx.android.synthetic.main.fragment_people_details.*

class PeopleDetailsFragment : BaseFragment() {
    private val mFilmUrl = "http://swapi.dev/api/films/"
    private val mSpeciesUrl = "http://swapi.dev/api/species/"
    private val mPlanetUrl = "http://swapi.dev/api/planets/"

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

    private lateinit var mPeopleDetailsViewModel: PeopleDetailsViewModel

    override fun layoutId() = R.layout.fragment_people_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        mPeopleDetailsViewModel = viewModel(viewModelFactory)
        {
            observe(mPeopleDetailMediatorLiveData, ::renderPeopleDetails)
            failure(failure, ::handleApiFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val peopleEntity = (arguments?.get(PARAM_PEOPLE_ENTITY) as PeopleEntity)
        mPeopleDetailsViewModel.updatePeopleDetailWithPeopleInfo(peopleEntity)

        showProgress()

        peopleEntity.homeworld?.let { it1 ->
            getIdFromUrl(it1).apply {
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
        var urlArrWithId = arrayOf(String.empty(), String.empty())

        if (url.contains(getString(R.string.peopleDetails_urlPart_film))) urlArrWithId =
            url.split(mFilmUrl).toTypedArray()
        else if (url.contains(getString(R.string.peopleDetails_urlPart_species))) urlArrWithId =
            url.split(mSpeciesUrl).toTypedArray()
        else if (url.contains(getString(R.string.peopleDetails_urlPart_planets))) urlArrWithId =
            url.split(mPlanetUrl).toTypedArray()


        var urlId = -1 // -1 tells that id is not available

        if (urlArrWithId.size > 1) {
            if (urlArrWithId[1].contains(getString(R.string.forward_slash))) {
                val filmIdStr = urlArrWithId[1].split(getString(R.string.forward_slash))[0]
                try {
                    urlId = filmIdStr.toInt()
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
            }

        }
        return urlId
    }


    private fun renderPeopleDetails(peopleDetailsDataModel: PeopleDetailsDataModel?) {

        hideProgress()

        peopleDetailsDataModel?.let {
            tv_peopleDetails_birth_year.text = it.birthYearNotNull

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                tv_peopleDetails_films.text =
                    Html.fromHtml(it.filmNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

                tv_peopleDetails_opening_crawl.text =
                    Html.fromHtml(it.openingCrawlNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

                tv_peopleDetails_homeworld.text =
                    Html.fromHtml(
                        it.homeworldNotNull +
                                getString(R.string.space) +
                                getString(R.string.peopleDetails_planetName),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )

                tv_peopleDetails_language.text =
                    Html.fromHtml(it.languagesNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

                tv_peopleDetails_speciesName.text =
                    Html.fromHtml(it.speciesNameNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

            } else {
                tv_peopleDetails_films.text = Html.fromHtml(it.filmNotNull)
                tv_peopleDetails_opening_crawl.text = Html.fromHtml(it.openingCrawlNotNull)

                tv_peopleDetails_homeworld.text = Html.fromHtml(it.homeworldNotNull)
                tv_peopleDetails_language.text = Html.fromHtml(it.languagesNotNull)
                tv_peopleDetails_speciesName.text = Html.fromHtml(it.speciesNameNotNull)
            }

            tv_peopleDetails_height.text = it.heightNotNull

            tv_peopleDetails_name.text = it.nameNotNull
            tv_peopleDetails_population.text = it.populationNotNull
        }
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
