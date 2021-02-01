package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.core.interactor.UseCase
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import javax.inject.Inject

class GetMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, None>() {

    override suspend fun run(params: None) = moviesRepository.movies()
}
