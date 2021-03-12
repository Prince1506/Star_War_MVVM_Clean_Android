package com.mvvm_clean.user_details.features.people_list.data.repo.response

import android.widget.ImageView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm_clean.user_details.core.base.KParcelable
import kotlinx.android.parcel.Parcelize


@BindingAdapter("profileImage")
fun loadImage(view: ImageView, avatar: String?) {
    Glide.with(view.context)
        .load(avatar).apply(RequestOptions().circleCrop())
        .into(view)
}
@Keep
@Parcelize
class Datum(
    var id: Int? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null,
    var fullName: String? = null
) : KParcelable{

    fun setFullName(){
        fullName = firstName + " " + lastName
    }

}