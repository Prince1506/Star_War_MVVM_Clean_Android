package com.mvvm_clean.star_wars.features.people_list.domain.api

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.data.NetworkHandler
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.core.domain.functional.onSuccess
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository.Network
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
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

    private val filmName = "filmName"
    private val planetName = "planetName"
    private val speciesName = "speciesName"
    private val peopleName = "Chewbacaa"
    private val filmId = 2
    private val speciesId = 1

    private lateinit var networkRepository: StarWarApiRepository.Network

    @MockK
    private lateinit var peopleListEntityMock: PeopleListResponseEntity

    @MockK
    private lateinit var filmResponseEntityMock: FilmResponseEntity

    @MockK
    private lateinit var peopleEntityMock: PeopleEntity

    @MockK
    private lateinit var planetListEntityMock: PlanetListResponseEntity

    @MockK
    private lateinit var planetListEntity: PlanetListEntity

    @MockK
    private lateinit var speciesResponseEntityMock: SpeciesResponseEntity

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: StarWarApiImpl

    @MockK
    private lateinit var speciesResponseCall: Call<SpeciesResponseEntity>

    @MockK
    private lateinit var filmResponseCall: Call<FilmResponseEntity>

    @MockK
    private lateinit var planetListResponseCall: Call<PlanetListResponseEntity>

    @MockK
    private lateinit var peopleListResponseCall: Call<PeopleListResponseEntity>

    @MockK
    private lateinit var speciesResponse: Response<SpeciesResponseEntity>

    @MockK
    private lateinit var filmResponse: Response<FilmResponseEntity>

    @MockK
    private lateinit var planetListResponse: Response<PlanetListResponseEntity>

    @MockK
    private lateinit var peopleListResponse: Response<PeopleListResponseEntity>


    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)
    }

    @Test
    fun `should return empty people list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns null
        every { peopleListResponse.isSuccessful } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(peopleName) } returns peopleListResponseCall

        val peopleEntity = networkRepository.getPeopleByQuery(peopleName)

        peopleEntity shouldEqual Right(emptyList<PeopleListDataModel>())
        verify(exactly = 1) { service.getPeopleListByQuery(peopleName) }
    }

    @Test
    fun `should return empty planet list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns null
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val planetEntity = networkRepository.getPlanetsByQuery(peopleName)

        planetEntity shouldEqual Right(emptyList<PlanetListDataModel>())
        verify(exactly = 1) { service.getPlanetListByQuery(peopleName) }
    }

    @Test
    fun `should return empty film list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.body() } returns null
        every { filmResponse.isSuccessful } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(filmId) } returns filmResponseCall

        val planetEntity = networkRepository.getFilmByQuery(filmId)

        planetEntity shouldEqual Right(emptyList<FilmDataModel>())
        verify(exactly = 1) { service.getFilmByQuery(filmId) }
    }

    @Test
    fun `should return empty species list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns null
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        val speciesEntity = networkRepository.getSpeciesByQuery(speciesId)

        speciesEntity shouldEqual Right(emptyList<PlanetListDataModel>())
        verify(exactly = 1) { service.getSpeciesByQuery(speciesId) }
    }


    @Test
    fun `people list api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val peopleEntity = networkRepository.getPeopleByQuery(peopleName)

        peopleEntity shouldBeInstanceOf Either::class.java
        peopleEntity.isLeft shouldEqual true
        peopleEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `species list api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val speciesEntity = networkRepository.getSpeciesByQuery(speciesId)

        speciesEntity shouldBeInstanceOf Either::class.java
        speciesEntity.isLeft shouldEqual true
        speciesEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `planet api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val planetEntity = networkRepository.getPlanetsByQuery(peopleName)

        planetEntity shouldBeInstanceOf Either::class.java
        planetEntity.isLeft shouldEqual true
        planetEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `film api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val filmEnitity = networkRepository.getFilmByQuery(filmId)

        filmEnitity shouldBeInstanceOf Either::class.java
        filmEnitity.isLeft shouldEqual true
        filmEnitity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `people list api should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.isSuccessful } returns false
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(peopleName) } returns peopleListResponseCall

        val movies = networkRepository.getPeopleByQuery(peopleName)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `species list api should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.isSuccessful } returns false
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        val movies = networkRepository.getSpeciesByQuery(speciesId)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `film list api should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.isSuccessful } returns false
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(filmId) } returns filmResponseCall

        val movies = networkRepository.getFilmByQuery(filmId)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `planet list api should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.isSuccessful } returns false
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val movies = networkRepository.getPlanetsByQuery(peopleName)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }


    @Test
    fun `people api request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(peopleName) } returns peopleListResponseCall

        val movies = networkRepository.getPeopleByQuery(peopleName)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `planet api request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val movies = networkRepository.getPlanetsByQuery(peopleName)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `film api request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(filmId) } returns filmResponseCall

        val movies = networkRepository.getFilmByQuery(filmId)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `species api request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        val movies = networkRepository.getSpeciesByQuery(speciesId)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }


    @Test
    fun `should get species list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns speciesResponseEntityMock
        every { speciesResponseEntityMock.name } returns speciesName
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        val speciesResponseEntity = networkRepository.getSpeciesByQuery(speciesId)

        speciesResponseEntity.onSuccess {
            it.name shouldEqual speciesName
        }

        verify(exactly = 1) { service.getPeopleListByQuery(peopleName) }
    }


    @Test
    fun `should get planet list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns planetListEntityMock
        every { planetListEntityMock.results } returns listOf(planetListEntity)
        every { planetListEntity.name } returns planetName
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val peopleListResponseEntity = networkRepository.getPlanetsByQuery(peopleName)

        peopleListResponseEntity.onSuccess {
            it.results?.get(0)?.name shouldEqual planetName
        }

        verify(exactly = 1) { service.getPeopleListByQuery(peopleName) }
    }


    @Test
    fun `should get people list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns peopleListEntityMock
        every { peopleListEntityMock.results } returns listOf(peopleEntityMock)
        every { peopleEntityMock.name } returns peopleName
        every { peopleListResponse.isSuccessful } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(peopleName) } returns peopleListResponseCall

        val peopleListResponseEntity = networkRepository.getPeopleByQuery(peopleName)

        peopleListResponseEntity.onSuccess {
            it.people?.get(0)?.name shouldEqual peopleName
        }

        verify(exactly = 1) { service.getPeopleListByQuery(peopleName) }
    }


    @Test
    fun `should get film list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.body() } returns filmResponseEntityMock
        every { filmResponseEntityMock.title } returns filmName
        every { filmResponse.isSuccessful } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(filmId) } returns filmResponseCall

        val filmResponseEntity = networkRepository.getFilmByQuery(filmId)

        filmResponseEntity.onSuccess {
            it.title shouldEqual filmName
        }

        verify(exactly = 1) { service.getFilmByQuery(filmId) }
    }

}