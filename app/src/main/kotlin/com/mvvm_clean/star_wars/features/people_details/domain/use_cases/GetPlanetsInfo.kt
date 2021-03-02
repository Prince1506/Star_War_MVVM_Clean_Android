package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import javax.inject.Inject


/**
 * Use case class to send data from view model layer to data layer.
 * This handles data flow from view module to repo
 */
class GetPlanetsInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PlanetListDataModel, Int>() {

    override suspend fun run(planetId: Int) =
        starWarApiRepository.getPlanetsByQuery(planetId)
}
