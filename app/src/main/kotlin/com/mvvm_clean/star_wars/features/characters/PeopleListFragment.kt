package com.mvvm_clean.star_wars.features.characters

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.exception.Failure
import com.mvvm_clean.star_wars.core.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.extension.*
import com.mvvm_clean.star_wars.core.navigation.Navigator
import com.mvvm_clean.star_wars.core.platform.BaseFragment
import com.mvvm_clean.star_wars.features.characters.PeopleListApiFailure.ListNotAvailable
import kotlinx.android.synthetic.main.fragment_people_list.*
import javax.inject.Inject

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
            observe(peopleListLiveData, ::renderPeopleList)
            failure(failure, ::handleFailure)
        }

        peopleListViewModel.getIsLoading()?.observe(
            this,
            object : Observer<Boolean?>
            {
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
        loadPeopleList()
    }


    private fun initializeView() {
        rv_people_list.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rv_people_list.adapter = peopleListAdapter
        peopleListAdapter.clickListener = { peopleInfo, navigationExtras ->
            navigator.showPeopleDetails(activity!!, peopleInfo, navigationExtras)
        }
    }

    private fun loadPeopleList() {
        emptyView.invisible()
        rv_people_list.visible()
        showProgress()
        peopleListViewModel.loadPeopleList()
    }

    private fun renderPeopleList(peoplseListView: PeoplseListView?) {
        if(peoplseListView?.result != null) {
            peopleListAdapter.collection = peoplseListView?.result
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
        notifyWithAction(message, R.string.action_refresh, ::loadPeopleList)
    }
}
