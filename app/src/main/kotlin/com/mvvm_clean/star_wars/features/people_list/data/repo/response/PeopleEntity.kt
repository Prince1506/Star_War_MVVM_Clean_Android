package com.mvvm_clean.star_wars.features.people_list.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.star_wars.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class variables can come null from API that's they are supporting nullables.
 */
@Parcelize
@Keep
data class PeopleEntity(
    var name: String? = null,
    var height: String? = null,
    var mass: String? = null,
    var hair_color: String? = null,
    var skin_color: String? = null,
    var eye_color: String? = null,
    var birth_year: String? = null,
    var gender: String? = null,
    var homeworld: String? = null,
    var films: ArrayList<String>? = null,
    var species: List<String>? = null,
    var vehicles: List<String>? = null,
    var starships: List<String>? = null,
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null,
): KParcelable