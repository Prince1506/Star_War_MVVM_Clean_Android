package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.core.interactor.UseCase
import com.fernandocejas.sample.features.movies.GetMovieDetails.Params
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieDetails, Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)

    data class Params(val id: Int)
}
