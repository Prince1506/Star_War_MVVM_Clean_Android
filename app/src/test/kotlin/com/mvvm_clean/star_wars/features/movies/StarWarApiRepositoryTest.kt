package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiImpl
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.extension.empty
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.core.data.NetworkHandler
import com.mvvm_clean.star_wars.features.people_list.*
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository.Network
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class StarWarApiRepositoryTest : UnitTest() {

    private lateinit var networkRepository: StarWarApiRepository.Network

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: StarWarApiImpl

    @MockK
    private lateinit var moviesCall: Call<List<PeopleListResponseEntity>>

    @MockK
    private lateinit var moviesResponse: Response<List<PeopleListResponseEntity>>

    @MockK
    private lateinit var movieDetailsCall: Call<MovieDetailsEntity>

    @MockK
    private lateinit var movieDetailsResponse: Response<MovieDetailsEntity>

    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)
    }

    @Test
    fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { moviesResponse.body() } returns null
        every { moviesResponse.isSuccessful } returns true
        every { moviesCall.execute() } returns moviesResponse
        every { service.getPeopleListByQuery() } returns moviesCall

        val movies = networkRepository.movies()

        movies shouldEqual Right(emptyList<PeopleListDataModel>())
        verify(exactly = 1) { service.getPeopleListByQuery() }
    }

    @Test
    fun `should get movie list from service`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { moviesResponse.body() } returns listOf(PeopleListResponseEntity(1, "poster"))
        every { moviesResponse.isSuccessful } returns true
        every { moviesCall.execute() } returns moviesResponse
        every { service.getPeopleListByQuery() } returns moviesCall

        val movies = networkRepository.movies()

        movies shouldEqual Right(listOf(PeopleListDataModel(1, "poster")))
        verify(exactly = 1) { service.getPeopleListByQuery() }
    }

    @Test
    fun `movies service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val movies = networkRepository.movies()

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verify { service wasNot Called }
    }

    @Test
    fun `movies service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { moviesResponse.isSuccessful } returns false
        every { moviesCall.execute() } returns moviesResponse
        every { service.getPeopleListByQuery() } returns moviesCall

        val movies = networkRepository.movies()

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `movies request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { moviesCall.execute() } returns moviesResponse
        every { service.getPeopleListByQuery() } returns moviesCall

        val movies = networkRepository.movies()

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `should return empty movie details by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { movieDetailsResponse.body() } returns null
        every { movieDetailsResponse.isSuccessful } returns true
        every { movieDetailsCall.execute() } returns movieDetailsResponse
        every { service.movieDetails(1) } returns movieDetailsCall

        val movieDetails = networkRepository.movieDetails(1)

        movieDetails shouldEqual Right(PeopleDetailsDataModel.empty)
        verify(exactly = 1) { service.movieDetails(1) }
    }

    @Test
    fun `should get movie details from service`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { movieDetailsResponse.body() } returns
                MovieDetailsEntity(
                    8, "title", String.empty(), String.empty(),
                    String.empty(), String.empty(), 0, String.empty()
                )
        every { movieDetailsResponse.isSuccessful } returns true
        every { movieDetailsCall.execute() } returns movieDetailsResponse
        every { service.movieDetails(1) } returns movieDetailsCall

        val movieDetails = networkRepository.movieDetails(1)

        movieDetails shouldEqual Right(
            PeopleDetailsDataModel(
                8, "title", String.empty(),
                String.empty(), String.empty(), String.empty(), 0, String.empty()
            )
        )
        verify(exactly = 1) { service.movieDetails(1) }
    }

    @Test
    fun `movie details service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val movieDetails = networkRepository.movieDetails(1)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `movie details service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { movieDetailsResponse.body() } returns null
        every { movieDetailsResponse.isSuccessful } returns false
        every { movieDetailsCall.execute() } returns movieDetailsResponse
        every { service.movieDetails(1) } returns movieDetailsCall

        val movieDetails = networkRepository.movieDetails(1)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `movie details request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { movieDetailsCall.execute() } returns movieDetailsResponse
        every { service.movieDetails(1) } returns movieDetailsCall

        val movieDetails = networkRepository.movieDetails(1)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}