package com.mvvm_clean.user_details.features.people_list.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel

/**
 * Data class variables can come null from API that's they are supporting nullables.
 */
@Keep
data class PeopleListResponseEntity(
    var page: Int? = null,
    var perPage: Int? = null,
    var totalPages: Int? = null,
    var data: List<Datum>?,
    var support: Support? = null
) {

    companion object {
        val empty = PeopleListResponseEntity(0, 0, 0, emptyList(), null)
    }

    fun toPeopleList() = PeopleListDataModel(page, perPage, totalPages, data, support)
}
