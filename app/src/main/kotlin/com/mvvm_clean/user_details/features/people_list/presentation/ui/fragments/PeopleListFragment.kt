package com.mvvm_clean.user_details.features.people_list.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm_clean.user_details.R
import com.mvvm_clean.user_details.core.base.BaseFragment
import com.mvvm_clean.user_details.core.domain.exception.Failure
import com.mvvm_clean.user_details.core.domain.extension.*
import com.mvvm_clean.user_details.core.presentation.navigation.Navigator
import com.mvvm_clean.user_details.features.login.presentation.fragments.mContext
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.user_details.features.people_list.presentation.models.PeopleListViewModel
import com.mvvm_clean.user_details.features.people_list.presentation.ui.adapter.PeopleListAdapter
import kotlinx.android.synthetic.main.fragment_people_list.*
import javax.inject.Inject

private const val NO_OF_COLUMNS = 1

/**
 * Fragment to show people list on screen.
 */
class PeopleListFragment : BaseFragment() {


    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var peopleListAdapter: PeopleListAdapter

    private lateinit var peopleListViewModel: PeopleListViewModel

    override fun layoutId() = R.layout.fragment_people_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()

        showProgress()
        peopleListViewModel.loadPeopleList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        peopleListViewModel = viewModel(viewModelFactory) {

            observe(getPeopleListLiveData(), ::renderPeopleListFragment)
            failure(getFailureLiveData(), ::handleFailure)
        }
        mContext = activity!!

    }


    private fun initializeView() {

        initRecycleViewForPeopleList()
    }

    private fun initRecycleViewForPeopleList() {

        rv_people_list.layoutManager = LinearLayoutManager(activity)
        rv_people_list.adapter = peopleListAdapter
        peopleListAdapter.mClickListener = { peopleInfo, navigationExtras ->
            navigator.showPeopleDetails(activity!!, peopleInfo)
        }
    }

    private fun renderPeopleList(peopleListView: PeopleListDataModel?) {
        activity?.let { hideKeyboard(it) }
        if (peopleListView?.data != null && peopleListView.data!!.isNotEmpty()) {
            peopleListAdapter.mCollection = peopleListView.data!!
        } else {
            renderFailure(R.string.empty_list)
        }
        rv_people_list.visible()
        hideProgress()
        super.handleApiSuccess()
    }

    private fun renderPeopleListFragment(peopleListDataModel: PeopleListDataModel?) {
        renderPeopleList(peopleListDataModel)
    }

    private fun handleFailure(failure: Failure?) {
        peopleListAdapter.mCollection = emptyList()
        activity?.let { hideKeyboard(it) }
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            else -> renderFailure(R.string.failure_people_list_unavailable)
        }
        super.handleApiFailure()
    }

    private fun renderFailure(@StringRes message: Int) {
        rv_people_list.gone()
        hideProgress()
        notifyWithAction(message)
        setPeopleListEmpty()
    }

    private fun setPeopleListEmpty() {
        peopleListAdapter.mCollection = emptyList()
    }

    private fun hideKeyboard(activity: FragmentActivity) {

        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus.apply {

            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (this == null) {
                View(activity)
            }
        }
        activity.inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_people_list.adapter = null
    }
}
