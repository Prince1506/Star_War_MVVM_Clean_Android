package com.mvvm_clean.star_wars.features.people_details.domain.models

data class PeopleDetailsDataModel(
    var speciesName: String? = "-",
    var birthYear: String? = "-",
    var height: String? = "-",
    var name: String? = "-",
    var languages: String? = "-",
    var homeworld: String? = "-",
    var population: String? = "-",
    var film: String? = "-",
    var openingCrawl: String? = "-",
)