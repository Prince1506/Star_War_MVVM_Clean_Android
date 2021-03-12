package com.mvvm_clean.user_details.features.people_list.data.repo.response

import androidx.annotation.Keep
import com.mvvm_clean.user_details.core.base.KParcelable
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class Datum(
    var id: Int? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null,
) : KParcelable