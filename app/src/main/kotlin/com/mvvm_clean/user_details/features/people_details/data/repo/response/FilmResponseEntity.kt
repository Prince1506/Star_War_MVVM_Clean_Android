package com.mvvm_clean.user_details.features.people_details.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.user_details.core.domain.extension.empty
import com.mvvm_clean.user_details.features.people_details.domain.models.FilmDataModel

/**
 * All data variables are nullable because API can return null values too.
 */
@Keep
data class FilmResponseEntity(
    val title: String? = null,
    val opening_crawl: String? = null,
) {

    companion object {
        val empty = FilmResponseEntity(String.empty(), null)
    }

    fun toFilmsDataModel() = FilmDataModel(title, opening_crawl)
}
