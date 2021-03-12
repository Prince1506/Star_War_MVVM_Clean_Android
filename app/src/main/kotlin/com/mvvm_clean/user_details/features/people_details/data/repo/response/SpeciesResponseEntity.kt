package com.mvvm_clean.user_details.features.people_details.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.user_details.core.domain.extension.empty
import com.mvvm_clean.user_details.features.people_details.domain.models.SpeciesDataModel

/**
 * All data variables are nullable because API can return null values too.
 */
@Keep
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
