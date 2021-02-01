package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.core.extension.empty

data class Movie(val id: Int, val poster: String) {

    companion object {
        val empty = Movie(0, String.empty())
    }
}
