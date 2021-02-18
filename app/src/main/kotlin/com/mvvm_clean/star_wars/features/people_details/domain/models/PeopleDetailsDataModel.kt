package com.mvvm_clean.star_wars.features.people_details.domain.models

import com.mvvm_clean.star_wars.core.domain.extension.isEmptyOrNull

data class PeopleDetailsDataModel(
    var speciesName: String? = "",
    var birthYear: String? = "-",
    var height: String? = "-",
    var name: String? = "-",
    var languages: String? = "",
    var homeworld: String? = "",
    var population: String? = "",
    var film: String? = "",
    var openingCrawl: String? = "",
) {
    val speciesNameNotNull: String?
        get() = if (String.isEmptyOrNull(speciesName)) "-" else speciesName

    val birthYearNotNull: String?
        get() = if (String.isEmptyOrNull(birthYear)) "-" else birthYear

    val nameNotNull: String?
        get() = if (String.isEmptyOrNull(name)) "-" else name

    val languagesNotNull: String?
        get() = if (String.isEmptyOrNull(languages)) "-" else languages


    val heightNotNull: String?
        get() = if (String.isEmptyOrNull(height)) "-" else height

    val homeworldNotNull: String?
        get() = if (String.isEmptyOrNull(homeworld)) "-" else homeworld

    val populationNotNull: String?
        get() = if (String.isEmptyOrNull(population)) "-" else population

    val filmNotNull: String?
        get() = if (String.isEmptyOrNull(film)) "-" else film

    val openingCrawlNotNull: String?
        get() = if (String.isEmptyOrNull(openingCrawl)) "-" else openingCrawl


    fun isEmpty() =
        String.isEmptyOrNull(speciesName) &&
                String.isEmptyOrNull(birthYear) &&
                String.isEmptyOrNull(height) &&
                String.isEmptyOrNull(name) &&
                String.isEmptyOrNull(languages) &&
                String.isEmptyOrNull(homeworld) &&
                String.isEmptyOrNull(population) &&
                String.isEmptyOrNull(film) &&
                String.isEmptyOrNull(openingCrawl)
}