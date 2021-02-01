package com.mvvm_clean.star_wars.features.movies

import android.os.Parcel
import com.mvvm_clean.star_wars.core.platform.KParcelable
import com.mvvm_clean.star_wars.core.platform.parcelableCreator

data class MovieView(val id: Int, val poster: String) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::MovieView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(poster)
        }
    }
}
