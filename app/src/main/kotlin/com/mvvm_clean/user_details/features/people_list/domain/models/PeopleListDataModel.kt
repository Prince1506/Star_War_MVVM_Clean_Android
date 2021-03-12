package com.mvvm_clean.user_details.features.people_list.domain.models

import com.mvvm_clean.user_details.features.people_list.data.repo.response.Datum
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.user_details.features.people_list.data.repo.response.Support

data class PeopleListDataModel(
    var page: Int? = null,
    var perPage: Int? = null,
    var totalPages: Int? = null,
    var data: List<Datum>?,
    var support: Support? = null
) {

    companion object {
        val empty = PeopleListResponseEntity(0, 0, 0, emptyList(), null)
    }
}
