package com.mvvm_clean.user_details.features.login.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.mvvm_clean.user_details.R
import com.mvvm_clean.user_details.core.base.BaseFragment
import com.mvvm_clean.user_details.core.domain.exception.Failure
import com.mvvm_clean.user_details.core.domain.extension.failure
import com.mvvm_clean.user_details.core.domain.extension.observe
import com.mvvm_clean.user_details.core.domain.extension.viewModel
import com.mvvm_clean.user_details.core.presentation.navigation.Navigator
import com.mvvm_clean.user_details.features.login.domain.Authenticator
import com.mvvm_clean.user_details.features.login.domain.models.LoginRequestDataModel
import com.mvvm_clean.user_details.features.login.presentation.view_models.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

lateinit var mContext: FragmentActivity

// Fragment corresponding to login screen.
class LoginFragment : BaseFragment() {

    lateinit var loginViewModel: LoginViewModel

    override fun layoutId() = R.layout.login_fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        loginViewModel = viewModel(viewModelFactory) {

            observe(getLoginResponseMutableLiveData(), ::renderLoginInfoData)
            failure(getFailureLiveData(), ::handleFailure)
        }
        mContext = activity!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b_login.setOnClickListener {
            val loginRequestDataModel = LoginRequestDataModel(
                tv_login_UserName_val.text.toString(),
                tv_login_email_val.text.toString()
            )
            loginViewModel.getUserLoginInfo(loginRequestDataModel)
        }

    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        hideProgress()
        notifyWithAction(message)
    }
}

fun renderLoginInfoData(doesUserExist: Boolean?) {
    if (doesUserExist != null && doesUserExist == true) {
        val authenticator = Authenticator()
        authenticator.setUserLoggedIn(doesUserExist)
        if (::mContext.isInitialized) {
            Navigator(authenticator).navigateToScreens(context = mContext)
        }
    }
}
