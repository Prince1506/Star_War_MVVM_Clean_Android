package com.mvvm_clean.user_details.features.people_list.presentation.ui.adapter

//class PeopleListAdapter
//@Inject constructor() : RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {
//
//    internal var mCollection: List<PeopleEntity> by Delegates.observable(emptyList()) { _, _, _ ->
//        notifyDataSetChanged()
//    }
//
//    internal var mClickListener: (PeopleEntity, Navigator.Extras) -> Unit = { _, _ -> }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        ViewHolder(parent.inflate(R.layout.people_list_item))
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
//        viewHolder.bind(mCollection.get(position), mClickListener)
//
//    override fun getItemCount() = mCollection.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(
//            peopleEntity: PeopleEntity,
//            clickListener: (PeopleEntity, Navigator.Extras) -> Unit
//        ) {
//
//            peopleEntity.name?.let { itemView.tv_people_name.text = it }
//            itemView.setOnClickListener {
//                clickListener(
//                    peopleEntity,
//                    Navigator.Extras()
//                )
//            }
//        }
//    }
//}
