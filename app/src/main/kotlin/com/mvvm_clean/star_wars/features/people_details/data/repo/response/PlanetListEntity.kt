package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlanetListEntity(

    // Attributes for Star war GetPeople API
    var name: String? = null,
    var rotation_period: String? = null,
    var orbital_period: String? = null,
    var diameter: String? = null,
    var climate: String? = null,
    var gravity: String? = null,
    var terrain: String? = null,
    var surface_water: String? = null,
    var population: String? = null,
    var residents: List<String>? = null,
    var films: List<String>? = null,
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null,
) : KParcelable