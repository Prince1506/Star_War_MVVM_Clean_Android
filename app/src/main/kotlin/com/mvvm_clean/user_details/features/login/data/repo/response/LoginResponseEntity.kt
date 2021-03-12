package com.mvvm_clean.user_details.features.login.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.user_details.features.login.domain.models.LoginResponseDataModel

/**
 * Data class variables can come null from API that's they are supporting nullables.
 */
@Keep
data class LoginResponseEntity(
    var page: Int? = null,
    var perPage: Int? = null,
    var totalPages: Int? = null,
    var data: List<DatumEntity>? = null,
    var support: Support? = null
) {

    companion object {
        val empty = LoginResponseEntity(0, 0, 0, emptyList(), null)
    }

    fun toLoginDatModel() = LoginResponseDataModel(page, perPage, totalPages, data, support)
}
