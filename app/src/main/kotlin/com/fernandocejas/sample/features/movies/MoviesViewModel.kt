package com.fernandocejas.sample.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandocejas.sample.core.interactor.UseCase.None
import com.fernandocejas.sample.core.platform.BaseViewModel
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    private val _movies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val movies: LiveData<List<MovieView>> = _movies

    fun loadMovies() = getMovies(None()) { it.fold(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        _movies.value = movies.map { MovieView(it.id, it.poster) }
    }
}