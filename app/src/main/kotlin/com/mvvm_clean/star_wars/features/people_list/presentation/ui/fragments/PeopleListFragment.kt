package com.mvvm_clean.star_wars.features.people_list.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.base.BaseFragment
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.*
import com.mvvm_clean.star_wars.core.presentation.navigation.Navigator
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.domain.repo.PeopleListApiFailure.ListNotAvailable
import com.mvvm_clean.star_wars.features.people_list.presentation.models.PeopleListViewModel
import com.mvvm_clean.star_wars.features.people_list.presentation.models.PeoplseListView
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.adapter.PeopleListAdapter
import kotlinx.android.synthetic.main.fragment_people_list.*
import javax.inject.Inject


class PeopleListFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var peopleListAdapter: PeopleListAdapter

    private lateinit var peopleListViewModel: PeopleListViewModel

    override fun layoutId() = R.layout.fragment_people_list
    val searchQuery = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        peopleListViewModel = viewModel(viewModelFactory) {
            observe(peopleListLiveData, ::renderPeopleList)
            failure(failure, ::handleFailure)
        }

        peopleListViewModel.getIsLoading()?.observe(
            this,
            object : Observer<Boolean?> {
                override fun onChanged(aBoolean: Boolean?) {
                    if (aBoolean!!) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }


    private fun initializeView() {
        rv_people_list.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        rv_people_list.adapter = peopleListAdapter
        peopleListAdapter.clickListener = { peopleInfo, navigationExtras ->
            navigator.showPeopleDetails(activity!!, peopleInfo, navigationExtras)
        }

        tv_peopleList_searchField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                peopleListViewModel.setSearchQueryString(tv_peopleList_searchField.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        /*addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filter(s.toString())
                }
            })*/
    }

    fun filter(text: String) {
        val temp: MutableList<PeopleEntity> = ArrayList()
        for (d in peopleListAdapter.collection) {
            if (d.name?.contains(text) == true) {
                temp.add(d)
            }
        }
        //update recyclerview
        peopleListAdapter.updateList(temp)
    }

    private fun loadPeopleList(searchQuery: String) {
        emptyView.invisible()
        rv_people_list.visible()
        showProgress()
        peopleListViewModel.loadPeopleList(searchQuery)
    }

    private fun renderPeopleList(peoplseListView: PeoplseListView?) {
        if (peoplseListView?.peopleList != null) {
            peopleListAdapter.collection = peoplseListView.peopleList
        }
        hideProgress()
    }


    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        rv_people_list.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh)
    }
}
