package com.mvvm_clean.star_wars.features.movies

import android.os.Parcel
import com.mvvm_clean.star_wars.core.platform.KParcelable
import com.mvvm_clean.star_wars.core.platform.parcelableCreator
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieView( val count: Int ?= null,
                      val next: String?= null,
                      val previous: String?= null,
                      val result: List<ResultEntity> ?= null
) : KParcelable