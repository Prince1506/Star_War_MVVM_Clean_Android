package com.mvvm_clean.star_wars.features.characters

import com.mvvm_clean.star_wars.core.interactor.UseCase
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import javax.inject.Inject

class GetPeopleInfo
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<PeopleListDataModel, None>() {

    override suspend fun run(params: None) = moviesRepository.movies("a")
}
