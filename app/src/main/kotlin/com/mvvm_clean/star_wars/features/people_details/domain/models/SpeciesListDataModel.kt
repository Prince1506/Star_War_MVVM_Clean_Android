package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.features.people_list.data.repo.response.SpeciesListEntity


data class SpeciesListDataModel(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: List<SpeciesListEntity>? = null,
)
