package com.mvvm_clean.star_wars.features.people_list.domain.models

import com.mvvm_clean.star_wars.features.people_list.data.repo.response.ResultEntity

data class PeopleListDataModel(val count: Int ?= null,
                               val next: String?= null,
                               val previous: String?= null,
                               val result: List<ResultEntity> ?= null
){

    companion object {
        val empty = PeopleListDataModel(0, null, null, emptyList())
    }
}
