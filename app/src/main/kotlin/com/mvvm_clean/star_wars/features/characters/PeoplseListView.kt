package com.mvvm_clean.star_wars.features.characters

import com.mvvm_clean.star_wars.core.platform.KParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeoplseListView(val count: Int ?= null,
                           val next: String?= null,
                           val previous: String?= null,
                           val result: List<ResultEntity> ?= null
) : KParcelable