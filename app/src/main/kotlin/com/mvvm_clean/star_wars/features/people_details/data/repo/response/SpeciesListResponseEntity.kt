package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesListDataModel


data class SpeciesListResponseEntity(
    private val count: Int? = null,
    private val next: String? = null,
    private val previous: String? = null,
    private val results: List<SpeciesListEntity>? = null,
) {

    companion object {
        val empty = SpeciesListResponseEntity(0, null, null, emptyList())
    }

    fun toSpeciesDataModel() = SpeciesListDataModel(count, next, previous, results)
}
