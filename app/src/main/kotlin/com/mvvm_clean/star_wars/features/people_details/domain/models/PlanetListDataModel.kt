package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListEntity


data class PlanetListDataModel(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PlanetListEntity>? = null
) {

    companion object {
        val empty = PlanetListDataModel(0, null, null, emptyList())
    }
}
