package com.mvvm_clean.star_wars.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import com.mvvm_clean.star_wars.core.platform.BaseViewModel
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    private val _movies: MutableLiveData<MovieView> = MutableLiveData()
    val movies: LiveData<MovieView> = _movies

    fun loadMovies() = getMovies(None()) { it.fold(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: Movie) {
        _movies.value =  MovieView(movies.count, movies.next, movies.previous, movies.result)
    }
}