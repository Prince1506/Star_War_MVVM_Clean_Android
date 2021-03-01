package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel


data class PlanetListResponseEntity(
    val name: String? = null,
    val population: String? = null,
) {

    companion object {
        val empty = PlanetListResponseEntity(name = null, population = null)
    }

    fun toPlanetsDataModel() = PlanetListDataModel(name = name, population = population)
}
