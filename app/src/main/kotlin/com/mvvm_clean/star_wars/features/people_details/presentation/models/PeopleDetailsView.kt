package com.mvvm_clean.star_wars.features.people_details.presentation.models

data class PeopleDetailsView(
    val id: Int,
    val title: String,
    val poster: String,
    val summary: String,
    val cast: String,
    val director: String,
    val year: Int,
    val trailer: String
)
