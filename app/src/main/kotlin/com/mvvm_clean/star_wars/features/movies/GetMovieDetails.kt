package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.core.interactor.UseCase
import com.mvvm_clean.star_wars.features.movies.GetMovieDetails.Params
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieDetails, Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)

    data class Params(val id: Int)
}
