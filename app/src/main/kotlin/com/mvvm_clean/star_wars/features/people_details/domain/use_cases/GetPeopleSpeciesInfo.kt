package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.core.domain.interactor.UseCase.None
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import javax.inject.Inject

class GetPeopleSpeciesInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PeopleListDataModel, None>() {

    override suspend fun run(params: None) = starWarApiRepository.movies("a")
}
