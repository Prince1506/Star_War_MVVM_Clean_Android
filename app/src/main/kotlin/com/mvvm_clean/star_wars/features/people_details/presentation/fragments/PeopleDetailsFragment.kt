package com.mvvm_clean.star_wars.features.people_details.presentation.fragments

import android.os.Bundle
import android.view.View
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.base.BaseFragment
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.close
import com.mvvm_clean.star_wars.core.domain.extension.failure
import com.mvvm_clean.star_wars.core.domain.extension.observe
import com.mvvm_clean.star_wars.core.domain.extension.viewModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.domain.repo.PeopleListApiFailure.NonExistentMovie
import kotlinx.android.synthetic.main.fragment_people_details.*

class PeopleDetailsFragment : BaseFragment() {

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

//    @Inject
//    lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private lateinit var peopleDetailsViewModel: PeopleDetailsViewModel

    override fun layoutId() = R.layout.fragment_people_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        peopleDetailsViewModel = viewModel(viewModelFactory)
        {
            observe(mPeopleDetailMediatorLiveData, ::renderPeopleDetails)
            failure(failure, ::handleApiFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPeopleEntity = (arguments?.get(PARAM_PEOPLE_ENTITY) as PeopleEntity)

        mPeopleEntity.name?.let {
            peopleDetailsViewModel.loadPlanetData(it)
            peopleDetailsViewModel.loadSpeciesData(it)
        }

    }


    private fun renderPeopleDetails(peopleDetailsDataModel: PeopleDetailsDataModel?) {


        peopleDetailsDataModel?.let {
            tv_peopleDetails_birth_year.text = it.birthYearNotNull
            tv_peopleDetails_films.text = it.filmNotNull
            tv_peopleDetails_height.text = it.heightNotNull
            tv_peopleDetails_homeworld.text = it.homeworldNotNull
            tv_peopleDetails_language.text = it.languagesNotNull
            tv_peopleDetails_name.text = it.nameNotNull
            tv_peopleDetails_opening_crawl.text = it.openingCrawlNotNull
            tv_peopleDetails_speciesName.text = it.speciesNameNotNull
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
