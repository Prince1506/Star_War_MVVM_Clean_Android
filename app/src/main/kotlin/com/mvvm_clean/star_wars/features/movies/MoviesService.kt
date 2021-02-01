package com.mvvm_clean.star_wars.features.movies

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : MoviesApi {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun movies(searchQuery :String) = moviesApi.movies(searchQuery)
    override fun movieDetails(movieId: Int) = moviesApi.movieDetails(movieId)
}
