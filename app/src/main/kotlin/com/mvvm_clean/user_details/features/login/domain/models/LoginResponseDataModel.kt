package com.mvvm_clean.user_details.features.login.domain.models

import com.mvvm_clean.user_details.features.login.data.repo.response.DatumEntity
import com.mvvm_clean.user_details.features.login.data.repo.response.Support
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PeopleListResponseEntity

data class LoginResponseDataModel(
    var page: Int? = null,
    var perPage: Int? = null,
    var totalPages: Int? = null,
    var data: List<DatumEntity>? = null,
    var support: Support? = null
) {

    companion object {
        val empty = PeopleListResponseEntity(0, 0, 0, emptyList(), null)
    }
}
