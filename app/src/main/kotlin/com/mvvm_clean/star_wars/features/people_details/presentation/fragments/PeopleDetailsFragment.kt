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
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.SpeciesListEntity
import com.mvvm_clean.star_wars.features.people_list.domain.repo.PeopleListApiFailure.NonExistentMovie
import kotlinx.android.synthetic.main.fragment_people_details.*

class PeopleDetailsFragment : BaseFragment() {

    companion object {
        const val PARAM_PEOPLE_ENTITY = "param_peopleEntity"

        fun forPeopleInfo(movie: SpeciesListEntity?): PeopleDetailsFragment {
            val movieDetailsFragment = PeopleDetailsFragment()
            movie?.let {
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

        peopleDetailsViewModel = viewModel(viewModelFactory) {
            observe(getPeopleDetailLiveData(), ::renderPeopleDetails)
            failure(failure, ::handleApiFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = (arguments?.get(PARAM_PEOPLE_ENTITY) as SpeciesListEntity).name
        name?.let {
            peopleDetailsViewModel.loadPlanetData(it)
            peopleDetailsViewModel.loadSpeciesData(it)
        }

//        } else {
//            movieDetailsAnimator.scaleUpView(moviePlay)
//            movieDetailsAnimator.cancelTransition(tv_people_name)
//            moviePoster.loadFromUrl((arguments!![PARAM_MOVIE] as ResultEntity).poster)
//        }
    }

    override fun onBackPressed() {
//        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
//        if (moviePlay.isVisible())
//            movieDetailsAnimator.scaleDownView(moviePlay)
//        else
//            movieDetailsAnimator.cancelTransition(tv_people_name)
    }

    private fun renderPeopleDetails(peopleDetailsDataModel: PeopleDetailsDataModel?) {
        peopleDetailsDataModel?.let {
            with(peopleDetailsDataModel) {
                tv_peopleDetails_birth_year.text = peopleDetailsDataModel.birthYearNotNull
                tv_peopleDetails_films.text = peopleDetailsDataModel.filmNotNull
                tv_peopleDetails_height.text = peopleDetailsDataModel.heightNotNull
                tv_peopleDetails_homeworld.text = peopleDetailsDataModel.homeworldNotNull
                tv_peopleDetails_language.text = peopleDetailsDataModel.languagesNotNull
                tv_peopleDetails_name.text = peopleDetailsDataModel.nameNotNull
                tv_peopleDetails_opening_crawl.text = peopleDetailsDataModel.openingCrawlNotNull
                tv_peopleDetails_speciesName.text = peopleDetailsDataModel.speciesNameNotNull
                tv_peopleDetails_population.text = peopleDetailsDataModel.populationNotNull
            }
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
