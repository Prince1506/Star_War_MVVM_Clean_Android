package com.mvvm_clean.star_wars.features.characters

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
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
            observe(peopleListLiveData, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        rv_people_list.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rv_people_list.adapter = peopleListAdapter
        peopleListAdapter.clickListener = { movie, navigationExtras ->
            navigator.showMovieDetails(activity!!, movie, navigationExtras)
        }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        rv_people_list.visible()
        showProgress()
        peopleListViewModel.loadPeopleList()
    }

    private fun renderMoviesList(movies: PeoplseListView?) {
        if(movies?.result != null) {
            peopleListAdapter.collection = movies?.result
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
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
