package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import javax.inject.Inject

/**
 * Use case class to send data from view model layer to data layer.
 * This handles data flow from view module to repo
 *
 */
class GetFilmNames
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<FilmDataModel, Int>() {

    override suspend fun run(filmId: Int) =
        starWarApiRepository.getFilmByQuery(filmId)
}
