package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.core.extension.empty

data class Movie(val id: Int, val poster: String) {

    companion object {
        val empty = Movie(0, String.empty())
    }
}
