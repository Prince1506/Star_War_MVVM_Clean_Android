package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.core.domain.extension.empty


/**
 * All data variables are nullable because API can return null values too.
 */
data class SpeciesDataModel(
    val name: String? = null,
    val homeworld: String? = null,
    val language: String? = null,
    val population: String? = null
) {

    companion object {
        val empty = SpeciesDataModel(String.empty(), null)
    }
}
