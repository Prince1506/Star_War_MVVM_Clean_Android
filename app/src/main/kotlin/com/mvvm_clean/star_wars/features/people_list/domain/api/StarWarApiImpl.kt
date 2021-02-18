package com.mvvm_clean.star_wars.features.people_list.domain.api

import com.mvvm_clean.star_wars.features.people_list.data.api.StarWarApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarApiImpl
@Inject constructor(retrofit: Retrofit) : StarWarApi {
    private val starWarApi by lazy { retrofit.create(StarWarApi::class.java) }

    override fun getPeopleListByQuery(searchQuery: String) =
        starWarApi.getPeopleListByQuery(searchQuery)

    override fun getSpeciesByQuery(speciesId: Int) =
        starWarApi.getSpeciesByQuery(speciesId)

    override fun getPlanetListByQuery(searchQuery: String) =
        starWarApi.getPlanetListByQuery(searchQuery)

    override fun getFilmByQuery(filmId: Int) =
        starWarApi.getFilmByQuery(filmId)

}
