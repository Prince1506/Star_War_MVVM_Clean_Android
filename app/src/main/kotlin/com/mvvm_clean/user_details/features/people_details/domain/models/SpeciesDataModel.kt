package com.mvvm_clean.user_details.features.people_details.domain.models

import com.mvvm_clean.user_details.core.domain.extension.empty


/**
 * All data variables are nullable because API can return null values too.
 *
 * Data models are the class which can contain manipulation according to
 * business needs.
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
