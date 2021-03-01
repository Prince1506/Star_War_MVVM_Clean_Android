package com.mvvm_clean.star_wars.features.people_list.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import javax.inject.Inject

class GetPeopleInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PeopleListDataModel, String>() {

    override suspend fun run(searchQuery: String) =
        starWarApiRepository.getPeopleByQuery(searchQuery)
}
