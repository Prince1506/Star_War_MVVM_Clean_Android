package com.mvvm_clean.star_wars.features.people_details.domain.models


/**
 * All data variables are nullable because API can return null values too.
 */
data class PlanetListDataModel(
    val name: String? = null,
    val population: String? = null,
) {

    companion object {
        val empty = PlanetListDataModel(null, null)
    }
}
