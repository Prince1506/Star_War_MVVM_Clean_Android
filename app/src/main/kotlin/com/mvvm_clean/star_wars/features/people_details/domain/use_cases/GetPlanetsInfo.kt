package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import javax.inject.Inject

class GetPlanetsInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PlanetListDataModel, String>() {

    override suspend fun run(searchQuery: String) =
        starWarApiRepository.getPlanetsByQuery(searchQuery)
}
