package com.mvvm_clean.star_wars.features.people_list.data.api

import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface StarWarApi {
    companion object {
        private const val PARAM_MOVIE_ID = "movieId"
        private const val PARAM_SEARCH_KEY = "search"
        private const val PATH_PEOPLE = "people/"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
    }

    @GET(PATH_PEOPLE)
    fun getPeopleListByQuery(@Query(PARAM_SEARCH_KEY) searchKey: String):  Call<PeopleListResponseEntity>
//
//    @GET(MOVIE_DETAILS)
//    fun movieDetails(@Path(PARAM_MOVIE_ID) movieId: Int): Call<MovieDetailsEntity>
}
