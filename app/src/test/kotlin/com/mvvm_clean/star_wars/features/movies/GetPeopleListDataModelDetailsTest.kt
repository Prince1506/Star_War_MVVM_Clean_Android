package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.features.people_list.GetPeopleDetails
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPeopleListDataModelDetailsTest : UnitTest() {

    private lateinit var getPeopleDetails: GetPeopleDetails

    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    @Before
    fun setUp() {
        getPeopleDetails = GetPeopleDetails(starWarApiRepository)
        every { starWarApiRepository.movieDetails(MOVIE_ID) } returns Right(PeopleDetailsDataModel.empty)
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getPeopleDetails.run(GetPeopleDetails.Params(MOVIE_ID)) }

        verify(exactly = 1) { starWarApiRepository.movieDetails(MOVIE_ID) }
    }

    companion object {
        private const val MOVIE_ID = 1
    }
}
