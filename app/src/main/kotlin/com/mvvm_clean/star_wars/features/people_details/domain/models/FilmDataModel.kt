package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.core.domain.extension.empty

/**
 * All data variables are nullable because API can return null values too.
 */
data class FilmDataModel(
    val title: String? = null,
    val opening_crawl: String? = null,
) {

    companion object {
        val empty = FilmDataModel(String.empty(), null)
    }
}
