package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import javax.inject.Inject

class GetSpeciesInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<SpeciesListDataModel, String>() {

    override suspend fun run(searchQuery: String) =
        starWarApiRepository.getSpeciesByQuery(searchQuery)
}
