package com.mvvm_clean.user_details.features.login.domain.use_cases

import com.mvvm_clean.user_details.core.domain.interactor.UseCase
import com.mvvm_clean.user_details.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel
import javax.inject.Inject

class GetLoginInfo
@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
    UseCase<PeopleListDataModel, UseCase.None>() {

    override suspend fun run(params: UseCase.None) =
        starWarApiRepository.getPeopleByQuery()
}
