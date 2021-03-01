package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesDataModel
import javax.inject.Inject

class GetSpeciesInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<SpeciesDataModel, Int>() {

    override suspend fun run(speciesId: Int) =
        starWarApiRepository.getSpeciesByQuery(speciesId)
}
