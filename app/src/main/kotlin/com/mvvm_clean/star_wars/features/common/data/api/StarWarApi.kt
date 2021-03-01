package com.mvvm_clean.star_wars.features.common.data.api

import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface StarWarApi {
    companion object {
        private const val PATH_PEOPLE = "people/"
        private const val PATH_SPECIES = "species/"
        private const val PATH_PLANETS = "planets/"
        private const val PATH_FILMS = "films/"
        private const val PATH_PARAM_PLANETS = "planets"
        private const val PATH_PARAM_FILMS = "films"
        private const val PATH_PARAM_SPECIES = "species"
        private const val QUERY_PARAM_SEARCH_KEY = "search"

    }

    @GET(PATH_PEOPLE)
    fun getPeopleListByQuery(@Query(QUERY_PARAM_SEARCH_KEY) searchKey: String): Call<PeopleListResponseEntity>

    @GET(PATH_PLANETS + "{planets}/")
    fun getPlanetListByQuery(@Path(PATH_PARAM_PLANETS) planetId: Int): Call<PlanetListResponseEntity>

    @GET(PATH_FILMS + "{films}/")
    fun getFilmByQuery(@Path(PATH_PARAM_FILMS) filmId: Int): Call<FilmResponseEntity>

    @GET(PATH_SPECIES + "{species}/")
    fun getSpeciesByQuery(@Path(PATH_PARAM_SPECIES) speciesId: Int): Call<SpeciesResponseEntity>
}
