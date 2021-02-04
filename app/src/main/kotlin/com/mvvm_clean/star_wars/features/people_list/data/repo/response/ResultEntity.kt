package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import com.mvvm_clean.star_wars.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultEntity (
    var name: String? = null,
    var height: String? = null,
    var mass: String? = null,
    var hairColor: String? = null,
    var skinColor: String? = null,
    var eyeColor: String? = null,
    var birthYear: String? = null,
    var gender: String? = null,
    var homeworld: String? = null,
    var films: List<String>? = null,
    var species: List<String>? = null,
    var vehicles: List<String>? = null,
    var starships: List<String>? = null,
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null,
): KParcelable