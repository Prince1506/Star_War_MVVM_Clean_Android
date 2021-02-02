package com.mvvm_clean.star_wars.features.characters

data class PeopleListResponseEntity(private val count: Int ?= null,
                                    private val next: String?= null,
                                    private val previous: String?= null,
                                    private val results: List<ResultEntity> ?= null
                       ) {

    fun toMovie() = PeopleListDataModel(count, next, previous, results)
}
