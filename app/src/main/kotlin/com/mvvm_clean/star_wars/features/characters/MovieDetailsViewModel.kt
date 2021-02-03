package com.mvvm_clean.star_wars.features.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.platform.BaseViewModel
import com.mvvm_clean.star_wars.features.characters.GetMovieDetails.Params
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(
    private val getMovieDetails: GetMovieDetails,
) : BaseViewModel() {

    private val _movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()
    val movieDetails: LiveData<MovieDetailsView> = _movieDetails

    fun loadMovieDetails(movieId: Int) =
        getMovieDetails(Params(movieId)) { it.fold(::handleFailure, ::handleMovieDetails) }


    private fun handleMovieDetails(movie: MovieDetails) {
        _movieDetails.value = MovieDetailsView(
            movie.id, movie.title, movie.poster,
            movie.summary, movie.cast, movie.director, movie.year, movie.trailer
        )
    }
}