package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel


data class PlanetListResponseEntity(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PlanetListEntity>? = null,
) {

    companion object {
        val empty = PlanetListResponseEntity(0, null, null, emptyList())
    }

    fun toPlanetsDataModel() = PlanetListDataModel(count, next, previous, results)
}
