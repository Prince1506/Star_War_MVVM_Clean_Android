package com.mvvm_clean.star_wars.features.people_list.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.core.domain.interactor.UseCase.None
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import javax.inject.Inject

class GetPeopleInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PeopleListDataModel, None>() {

    override suspend fun run(params: None) = starWarApiRepository.movies("a")
}
