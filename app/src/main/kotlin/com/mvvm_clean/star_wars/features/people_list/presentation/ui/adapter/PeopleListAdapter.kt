package com.mvvm_clean.star_wars.features.people_list.presentation.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.domain.extension.inflate
import com.mvvm_clean.star_wars.core.presentation.navigation.Navigator
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.SpeciesListEntity
import kotlinx.android.synthetic.main.row_people_info.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PeopleListAdapter
@Inject constructor() : RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {

    internal var collection: List<SpeciesListEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (SpeciesListEntity, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_people_info))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection.get(position), clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            peopleListEntity: SpeciesListEntity,
            clickListener: (SpeciesListEntity, Navigator.Extras) -> Unit
        ) {
            peopleListEntity.name?.let { itemView.tv_people_name.text = it }
            itemView.setOnClickListener {
                clickListener(
                    peopleListEntity,
                    Navigator.Extras(itemView.tv_people_name)
                )
            }
        }
    }
}
