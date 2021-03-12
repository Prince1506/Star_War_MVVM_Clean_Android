package com.mvvm_clean.user_details.features.people_details.domain.models


/**
 * All data variables are nullable because API can return null values too.
 * Data models are the class which can contain manipulation according to
 * business needs.
 */
data class PlanetListDataModel(
    val name: String? = null,
    val population: String? = null,
) {

    companion object {
        val empty = PlanetListDataModel(null, null)
    }
}
