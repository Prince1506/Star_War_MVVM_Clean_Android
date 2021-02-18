package com.mvvm_clean.star_wars.features.people_list.domain.api

import com.mvvm_clean.star_wars.BuildConfig
import com.mvvm_clean.star_wars.core.data.NetworkHandler
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.core.domain.functional.Either.Left
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesListDataModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.SpeciesListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import retrofit2.Call
import javax.inject.Inject

interface StarWarApiRepository {
    fun getPeopleByQuery(searchQuery: String): Either<Failure, PeopleListDataModel>
    fun getSpeciesByQuery(searchQuery: String): Either<Failure, SpeciesListDataModel>
    fun getPlanetsByQuery(searchQuery: String): Either<Failure, PlanetListDataModel>
    fun getFilmByQuery(filmId: Int): Either<Failure, FilmDataModel>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StarWarApiImpl
    ) : StarWarApiRepository {

        override fun getPeopleByQuery(searchQuery: String): Either<Failure, PeopleListDataModel> =
            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getPeopleListByQuery(searchQuery),
                    { it.toPeopleList() },
                    PeopleListResponseEntity.empty
                )
                false -> Left(NetworkConnection)
            }

        override fun getSpeciesByQuery(searchQuery: String): Either<Failure, SpeciesListDataModel> =
            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getSpeciesListByQuery(searchQuery),
                    { it.toSpeciesDataModel() },
                    SpeciesListResponseEntity.empty
                )
                false -> Left(NetworkConnection)
            }

        override fun getPlanetsByQuery(searchQuery: String): Either<Failure, PlanetListDataModel> =

            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getPlanetListByQuery(searchQuery),
                    { it.toPlanetsDataModel() },
                    PlanetListResponseEntity.empty
                )
                false -> Left(NetworkConnection)
            }

        override fun getFilmByQuery(filmId: Int): Either<Failure, FilmDataModel> =

            when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.getFilmByQuery(filmId),
                    { it.toFilmsDataModel() },
                    FilmResponseEntity.empty
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

                if (BuildConfig.DEBUG)
                    exception.printStackTrace()

                Left(ServerError)
            }
        }
    }
}
