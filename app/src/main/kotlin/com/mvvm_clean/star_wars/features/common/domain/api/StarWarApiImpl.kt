package com.mvvm_clean.star_wars.features.common.domain.api

import com.mvvm_clean.star_wars.features.common.data.api.StarWarApi
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

    override fun getPeopleListByQuery(searchQuery: String) =
        starWarApi.getPeopleListByQuery(searchQuery)

    override fun getSpeciesByQuery(speciesId: Int) =
        starWarApi.getSpeciesByQuery(speciesId)

    override fun getPlanetListByQuery(planetId: Int) =
        starWarApi.getPlanetListByQuery(planetId)

    override fun getFilmByQuery(filmId: Int) =
        starWarApi.getFilmByQuery(filmId)

}
