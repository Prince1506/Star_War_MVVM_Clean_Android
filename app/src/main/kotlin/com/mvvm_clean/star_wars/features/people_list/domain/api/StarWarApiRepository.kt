package com.mvvm_clean.star_wars.features.people_list.domain.api

import com.mvvm_clean.star_wars.BuildConfig
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.core.domain.functional.Either.Left
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.core.data.NetworkHandler
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import retrofit2.Call
import javax.inject.Inject

interface StarWarApiRepository {
    fun movies(searchQuery:String): Either<Failure, PeopleListDataModel>
//    fun movieDetails(movieId: String): Either<Failure, PeopleDetailsDataModel>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StarWarApiImpl
    ) : StarWarApiRepository {

        override fun movies(searchQuery:String): Either<Failure, PeopleListDataModel> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getPeopleListByQuery(searchQuery),
                    { it.toMovie()},
                    PeopleListResponseEntity(0, null, null, emptyList())
                )
                false -> Left(NetworkConnection)
            }
        }

//        override fun movieDetails(movieId: String): Either<Failure, PeopleDetailsDataModel> {
//            return when (networkHandler.isNetworkAvailable()) {
//                true -> request(
//                    service.movieDetails(movieId),
//                    { it.toMovieDetails() },
//                    MovieDetailsEntity.empty
//                )
//                false -> Left(NetworkConnection)
//            }
//        }

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

                if (BuildConfig.DEBUG)
                    exception.printStackTrace()

                Left(ServerError)
            }
        }
    }
}
