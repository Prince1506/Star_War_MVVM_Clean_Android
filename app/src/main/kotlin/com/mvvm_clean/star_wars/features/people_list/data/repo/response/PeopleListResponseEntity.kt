package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.squareup.moshi.Json


data class PeopleListResponseEntity(
    private val count: Int? = null,
    private val next: String? = null,
    private val previous: String? = null,

    @Json(name = "results")
    private val results: List<PeopleEntity>? = null,
) {

    companion object {
        val empty = PeopleListResponseEntity(0, null, null, emptyList())
    }

    fun toPeopleList() = PeopleListDataModel(count, next, previous, results)
}
