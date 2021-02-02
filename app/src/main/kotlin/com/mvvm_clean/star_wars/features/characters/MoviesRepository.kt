package com.mvvm_clean.star_wars.features.characters

import com.mvvm_clean.star_wars.BuildConfig
import com.mvvm_clean.star_wars.core.exception.Failure
import com.mvvm_clean.star_wars.core.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.functional.Either
import com.mvvm_clean.star_wars.core.functional.Either.Left
import com.mvvm_clean.star_wars.core.functional.Either.Right
import com.mvvm_clean.star_wars.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    fun movies(searchQuery:String): Either<Failure, PeopleListDataModel>
    fun movieDetails(movieId: Int): Either<Failure, MovieDetails>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StarWarApiImpl
    ) : MoviesRepository {

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

        override fun movieDetails(movieId: Int): Either<Failure, MovieDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.movieDetails(movieId),
                    { it.toMovieDetails() },
                    MovieDetailsEntity.empty
                )
                false -> Left(NetworkConnection)
            }
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

                if (BuildConfig.DEBUG)
                    exception.printStackTrace()

                Left(ServerError)
            }
        }
    }
}
