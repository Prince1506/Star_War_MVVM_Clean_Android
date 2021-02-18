package com.mvvm_clean.star_wars.features.people_details.data.repo.response

import com.mvvm_clean.star_wars.core.domain.extension.empty


data class SpeciesResponseEntity(
    val name: String? = null,
    val homeworld: String? = null,
    val language: String? = null,
    val population: String? = null
) {

    companion object {
        val empty = SpeciesResponseEntity(String.empty(), null)
    }

    fun toSpeciesDataModel() = SpeciesDataModel(name, homeworld, language, population)
}
