package com.mvvm_clean.user_details.features.login.presentation.view_models

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.user_details.core.base.BaseViewModel
import com.mvvm_clean.user_details.core.domain.interactor.UseCase
import com.mvvm_clean.user_details.features.login.domain.models.LoginRequestDataModel
import com.mvvm_clean.user_details.features.login.domain.models.LoginResponseDataModel
import com.mvvm_clean.user_details.features.people_details.domain.use_cases.GetLoginInfo
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val getLoginInfo: GetLoginInfo,
) : BaseViewModel() {

    private lateinit var mLoginRequest: LoginRequestDataModel
    private val loginResponseLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val loginResponseMutableLiveData: LiveData<Boolean> = loginResponseLiveData

    fun getLoginResponseMutableLiveData() = loginResponseMutableLiveData

    fun getUserLoginInfo(loginRequest: LoginRequestDataModel) {
        mLoginRequest = loginRequest
        getLoginInfo(UseCase.None()) { it.fold(::handleFailure, ::handleLoginData) }
    }


    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handleLoginData(loginDataModel: LoginResponseDataModel) {
        loginResponseLiveData.value = doesUserExist(loginDataModel)
    }

    private fun doesUserExist(loginDataModel: LoginResponseDataModel): Boolean {
        val data = loginDataModel.data
        if (data != null) {
            for (userData in data) {
                if (userData.name.equals(mLoginRequest.userName)) {
                    return true
                }
            }
        }
        return false
    }
}

