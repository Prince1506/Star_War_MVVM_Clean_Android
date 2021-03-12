package com.mvvm_clean.user_details.features.common.domain.api

import com.mvvm_clean.user_details.features.common.data.api.StarWarApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of all APIs
 */
@Singleton
class StarWarApiImpl
@Inject constructor(retrofit: Retrofit) : StarWarApi {
    private val starWarApi by lazy { retrofit.create(StarWarApi::class.java) }

    override fun getPeopleListByQuery() =
        starWarApi.getPeopleListByQuery()


    override fun getLoginInfo() =
        starWarApi.getLoginInfo()


}
