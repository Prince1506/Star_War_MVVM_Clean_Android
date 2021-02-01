package com.mvvm_clean.star_wars.features.movies

data class MovieDetailsView(
    val id: Int,
    val title: String,
    val poster: String,
    val summary: String,
    val cast: String,
    val director: String,
    val year: Int,
    val trailer: String
)
