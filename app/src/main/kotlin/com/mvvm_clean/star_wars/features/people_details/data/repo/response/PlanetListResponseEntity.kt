package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.squareup.moshi.Json


data class PlanetListResponseEntity(
    private val count: Int? = null,
    private val next: String? = null,
    private val previous: String? = null,

    @Json(name = "results")
    private val results: List<PlanetListEntity>? = null,
) {

    companion object {
        val empty = PlanetListResponseEntity(0, null, null, emptyList())
    }

    fun toPlanetsDataModel() = PlanetListDataModel(count, next, previous, results)
}
