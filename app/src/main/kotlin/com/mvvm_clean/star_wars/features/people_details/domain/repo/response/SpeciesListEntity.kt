package com.mvvm_clean.star_wars.features.people_details.domain.repo.response

import com.mvvm_clean.star_wars.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpeciesListEntity(

    // Attributes for Star war GetPeople API
    var name: String? = null,
    var classification: String? = null,
    var designation: String? = null,
    var average_height: String? = null,
    var skin_colors: String? = null,
    var hair_colors: String? = null,
    var eye_colors: String? = null,
    var average_lifespan: String? = null,
    var homeworld: String? = null,
    var language: List<String>? = null,
    var people: List<String>? = null,
    var films: List<String>? = null,
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null,
) : KParcelable