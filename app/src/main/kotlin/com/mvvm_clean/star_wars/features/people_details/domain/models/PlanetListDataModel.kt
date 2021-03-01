package com.mvvm_clean.star_wars.features.people_details.domain.models


data class PlanetListDataModel(
    val name: String? = null,
    val population: String? = null,
) {

    companion object {
        val empty = PlanetListDataModel(null, null)
    }
}
