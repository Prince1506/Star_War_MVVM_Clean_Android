package com.mvvm_clean.star_wars.features.people_list.presentation.models

import com.mvvm_clean.star_wars.core.base.KParcelable
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.ResultEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeoplseListView(val count: Int ?= null,
                           val next: String?= null,
                           val previous: String?= null,
                           val result: List<ResultEntity> ?= null
) : KParcelable