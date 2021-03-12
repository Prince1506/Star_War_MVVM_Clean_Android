package com.mvvm_clean.user_details.features.people_list.presentation.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm_clean.user_details.R
import com.mvvm_clean.user_details.core.domain.extension.inflate
import com.mvvm_clean.user_details.core.presentation.navigation.Navigator
import com.mvvm_clean.user_details.features.people_list.data.repo.response.Datum
import kotlinx.android.synthetic.main.people_list_item.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PeopleListAdapter
@Inject constructor() : RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {

    internal var mCollection: List<Datum> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var mClickListener: (Datum, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.people_list_item))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(mCollection.get(position), mClickListener)

    override fun getItemCount() = mCollection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            datum: Datum,
            clickListener: (Datum, Navigator.Extras) -> Unit
        ) {

            datum.email?.let { itemView.tv_people_name.text = it }
            itemView.setOnClickListener {
                clickListener(
                    datum,
                    Navigator.Extras()
                )
            }
        }
    }
}
