package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.core.domain.extension.empty

data class PeopleDetailsDataModel(
    val id: Int,
    val title: String,
    val poster: String,
    val summary: String,
    val cast: String,
    val director: String,
    val year: Int,
    val trailer: String
) {

    companion object {
        val empty = PeopleDetailsDataModel(
            0, String.empty(), String.empty(), String.empty(),
            String.empty(), String.empty(), 0, String.empty()
        )
    }
}
