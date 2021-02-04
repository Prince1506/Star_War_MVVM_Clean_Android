package com.mvvm_clean.star_wars.features.people_details.presentation.fragments

import android.os.Bundle
import android.view.View
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.*
import com.mvvm_clean.star_wars.core.base.BaseFragment
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsView
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.domain.repo.PeopleListApiFailure.NonExistentMovie
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.ResultEntity

class PeopleDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_MOVIE = "param_movie"

        fun forPeopleInfo(movie: ResultEntity?): PeopleDetailsFragment {
            val movieDetailsFragment = PeopleDetailsFragment()
            movie?.let {
                val arguments = Bundle()
                arguments.putParcelable(PARAM_MOVIE, it)
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
            observe(peopleDetailsLiveData, ::renderPeopleDetails)
            failure(failure, ::handleApiFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = (arguments?.get(PARAM_MOVIE) as ResultEntity).name
        name?.let { peopleDetailsViewModel.loadPeopleDetails(it) }

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

    private fun renderPeopleDetails(people: PeopleDetailsView?) {
        people?.let {
            with(people) {
//                activity?.let {
//                    tv_people_name.loadUrlAndPostponeEnterTransition(poster, it)
//                    it.toolbar.title = title
//                }
//                movieSummary.text = summary
//                movieCast.text = cast
//                movieDirector.text = director
//                movieYear.text = year.toString()
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
