package com.mvvm_clean.star_wars.features.movies

data class Movie(val count: Int ?= null,
                 val next: String?= null,
                 val previous: String?= null,
                 val result: List<ResultEntity> ?= null
){

    companion object {
        val empty = Movie(0, null, null, emptyList())
    }
}
