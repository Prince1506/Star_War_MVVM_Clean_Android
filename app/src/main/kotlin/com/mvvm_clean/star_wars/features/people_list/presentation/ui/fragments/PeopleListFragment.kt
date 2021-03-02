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
import com.mvvm_clean.star_wars.features.common.domain.models.ApiFailure.ListNotAvailable
import com.mvvm_clean.star_wars.features.people_list.presentation.models.PeopleListView
import com.mvvm_clean.star_wars.features.people_list.presentation.models.PeopleListViewModel
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.adapter.PeopleListAdapter
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.registers.CountingIdlingResourceSingleton
import kotlinx.android.synthetic.main.fragment_people_list.*
import javax.inject.Inject

private const val NO_OF_COLUMNS = 1
class PeopleListFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var peopleListAdapter: PeopleListAdapter

    private lateinit var peopleListViewModel: PeopleListViewModel

    override fun layoutId() = R.layout.fragment_people_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        peopleListViewModel = viewModel(viewModelFactory) {
            observe(getPeopleListLiveData(), ::renderPeopleList)
            failure(getFailureLiveData(), ::handleFailure)
        }

        peopleListViewModel.getProgressLoadingLiveData().observe(
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

        initRecycleViewForPeopleList()
        initSearchPeopleNameListener()

    }

    private fun initSearchPeopleNameListener() {

        tv_peopleList_searchField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CountingIdlingResourceSingleton.increment()
                peopleListViewModel.setSearchQueryString(tv_peopleList_searchField.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initRecycleViewForPeopleList() {

        rv_people_list.layoutManager = StaggeredGridLayoutManager(
            NO_OF_COLUMNS,
            StaggeredGridLayoutManager.VERTICAL
        )
        rv_people_list.adapter = peopleListAdapter
        peopleListAdapter.mClickListener = { peopleInfo, navigationExtras ->
            navigator.showPeopleDetails(activity!!, peopleInfo)
        }
    }

    private fun renderPeopleList(peopleListView: PeopleListView?) {

        if (peopleListView?.peopleList != null) {
            peopleListAdapter.mCollection = peopleListView.peopleList
        }
        hideProgress()
        super.handleApiSuccess()
    }


    private fun handleFailure(failure: Failure?) {

        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_people_list_unavailable)
        }
        super.handleApiFailure()
    }

    private fun renderFailure(@StringRes message: Int) {

        rv_people_list.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message)

    }
}
