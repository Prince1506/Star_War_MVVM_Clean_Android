package com.mvvm_clean.star_wars.features.characters

import com.mvvm_clean.star_wars.core.extension.empty

data class MovieDetails(
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
        val empty = MovieDetails(
            0, String.empty(), String.empty(), String.empty(),
            String.empty(), String.empty(), 0, String.empty()
        )
    }
}
