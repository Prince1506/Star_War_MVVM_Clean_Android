package com.mvvm_clean.star_wars.features.characters

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarApiImpl
@Inject constructor(retrofit: Retrofit) : StarWarApi {
    private val starWarApi by lazy { retrofit.create(StarWarApi::class.java) }

    override fun getPeopleListByQuery(searchQuery :String) = starWarApi.getPeopleListByQuery(searchQuery)
    override fun movieDetails(movieId: Int) = starWarApi.movieDetails(movieId)
}
