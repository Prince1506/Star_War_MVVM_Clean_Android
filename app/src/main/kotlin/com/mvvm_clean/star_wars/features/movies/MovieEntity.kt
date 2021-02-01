package com.mvvm_clean.star_wars.features.movies

data class MovieEntity(private val count: Int ?= null,
                       private val next: String?= null,
                       private val previous: String?= null,
                       private val results: List<ResultEntity> ?= null
                       ) {

    fun toMovie() = Movie(count, next, previous, results)
}
