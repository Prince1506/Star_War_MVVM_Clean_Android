package com.mvvm_clean.user_details.features.common.domain.api

import com.mvvm_clean.user_details.BuildConfig
import com.mvvm_clean.user_details.core.data.NetworkHandler
import com.mvvm_clean.user_details.core.domain.exception.Failure
import com.mvvm_clean.user_details.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.user_details.core.domain.exception.Failure.ServerError
import com.mvvm_clean.user_details.core.domain.functional.Either
import com.mvvm_clean.user_details.core.domain.functional.Either.Left
import com.mvvm_clean.user_details.core.domain.functional.Either.Right
import com.mvvm_clean.user_details.features.login.data.repo.response.LoginResponseEntity
import com.mvvm_clean.user_details.features.login.domain.models.LoginResponseDataModel
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel
import retrofit2.Call
import javax.inject.Inject

/**
 * Base Repo to handle all Data related operations.
 */
interface StarWarApiRepository {

    fun getPeopleByQuery(): Either<Failure, PeopleListDataModel>
    fun getLoginInfo(): Either<Failure, LoginResponseDataModel>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StarWarApiImpl
    ) : StarWarApiRepository {

        override fun getPeopleByQuery(): Either<Failure, PeopleListDataModel> =

            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getPeopleListByQuery(),
                    { it.toPeopleList() },
                    PeopleListResponseEntity.empty
                )
                false -> Left(NetworkConnection)
            }

        override fun getLoginInfo(): Either<Failure, LoginResponseDataModel> =

            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getLoginInfo(),
                    { it.toLoginDatModel() },
                    LoginResponseEntity.empty
                )
                false -> Left(NetworkConnection)
            }


        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {

            return try {

                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {

                if (BuildConfig.DEBUG) exception.printStackTrace()
                Left(ServerError)
            }
        }
    }
}
