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
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.domain.repo.PeopleListApiFailure.NonExistentMovie
import kotlinx.android.synthetic.main.fragment_people_details.*

class PeopleDetailsFragment : BaseFragment() {
    private val filmUrl = "http://swapi.dev/api/films/"
    private val speciesUrl = "http://swapi.dev/api/species/"
    private val PATH_FILMS = "films/"

    companion object {
        const val PARAM_PEOPLE_ENTITY = "param_peopleEntity"

        fun forPeopleInfo(speciesEntity: PeopleEntity?): PeopleDetailsFragment {
            val movieDetailsFragment = PeopleDetailsFragment()
            speciesEntity?.let {
                val arguments = Bundle()
                arguments.putParcelable(PARAM_PEOPLE_ENTITY, it)
                movieDetailsFragment.arguments = arguments
            }
            return movieDetailsFragment
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
        peopleEntity.name?.let {
            mPeopleDetailsViewModel.loadPlanetData(it)
        }

        peopleEntity.let {
            for (film in it.films!!) {
                getFilmId(film).apply {
                    if (this != -1)
                        mPeopleDetailsViewModel.getFilmsFromId(this)
                }

            }
            for (species in it.species!!) {
                getFilmId(species).apply {
                    if (this != -1) mPeopleDetailsViewModel.loadSpeciesData(this)
                }
            }
        }

    }

    private fun getFilmId(film: String): Int {
        var filmIdArr = arrayOf(String.empty(), String.empty())

        if (film.contains("film")) filmIdArr = film.split(filmUrl).toTypedArray()
        else if (film.contains("species")) filmIdArr = film.split(speciesUrl).toTypedArray()


        var filmId = -1 // -1 tells that id is not available

        if (filmIdArr.size > 1) {
            if (filmIdArr[1].contains(getString(R.string.forward_slash))) {
                val filmIdStr = filmIdArr[1].split(getString(R.string.forward_slash))[0]
                try {
                    filmId = filmIdStr.toInt()
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
            }

        }
        return filmId
    }


    private fun renderPeopleDetails(peopleDetailsDataModel: PeopleDetailsDataModel?) {


        peopleDetailsDataModel?.let {
            tv_peopleDetails_birth_year.text = it.birthYearNotNull

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                tv_peopleDetails_films.text =
                    Html.fromHtml(it.filmNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

                tv_peopleDetails_opening_crawl.text =
                    Html.fromHtml(it.openingCrawlNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

                tv_peopleDetails_homeworld.text =
                    Html.fromHtml(it.homeworldNotNull, HtmlCompat.FROM_HTML_MODE_COMPACT)

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
//        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
//        movieDetailsAnimator.scaleUpView(moviePlay)
    }


    private fun handleApiFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentMovie -> {
                notify(R.string.failure_movie_non_existent); close()
            }
        }
    }

}
