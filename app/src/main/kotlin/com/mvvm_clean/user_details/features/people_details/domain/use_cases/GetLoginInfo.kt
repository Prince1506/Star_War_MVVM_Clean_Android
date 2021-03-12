package com.mvvm_clean.user_details.features.people_details.domain.use_cases

import com.mvvm_clean.user_details.core.domain.interactor.UseCase
import com.mvvm_clean.user_details.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.user_details.features.login.domain.models.LoginResponseDataModel
import javax.inject.Inject


/**
 * Use case class to send data from view model layer to data layer.
 * This handles data flow from view module to repo
 */
class GetLoginInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<LoginResponseDataModel, UseCase.None>() {

    override suspend fun run(params: UseCase.None) =
        starWarApiRepository.getLoginInfo()
}
