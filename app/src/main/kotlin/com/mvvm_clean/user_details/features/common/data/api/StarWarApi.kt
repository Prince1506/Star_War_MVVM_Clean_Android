package com.mvvm_clean.user_details.features.common.data.api

import com.mvvm_clean.user_details.features.login.data.repo.response.LoginResponseEntity
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PeopleListResponseEntity
import retrofit2.Call
import retrofit2.http.GET

/**
 * API interface to handle all API calls
 */
internal interface StarWarApi {

    companion object {

        private const val PATH_PEOPLE = "users/"
        private const val PATH_LOGIN = "login/"
    }

    @GET(PATH_PEOPLE)
    fun getPeopleListByQuery(): Call<PeopleListResponseEntity>

    @GET(PATH_LOGIN)
    fun getLoginInfo(): Call<LoginResponseEntity>

}
