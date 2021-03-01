package com.mvvm_clean.star_wars.features.common.domain.api

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.data.NetworkHandler
import com.mvvm_clean.star_wars.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.star_wars.core.domain.exception.Failure.ServerError
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.core.domain.functional.onSuccess
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository.Network
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListResponseEntity
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class StarWarApiRepositoryTest : UnitTest() {

    private lateinit var planetEntity: PlanetListEntity
    private lateinit var speciesResponseEntity: SpeciesResponseEntity
    private lateinit var planetListEntity: PlanetListResponseEntity
    private lateinit var peopleListEntity: PeopleListResponseEntity
    private lateinit var peopleEntity: PeopleEntity
    private lateinit var filmResponseEntity: FilmResponseEntity

    private val filmName = "filmName"
    private val planetName = "planetName"
    private val speciesName = "speciesName"
    private val peopleName = "Chewbacaa"
    private val filmId = 2
    private val speciesId = 1

    private lateinit var networkRepository: StarWarApiRepository.Network

    @RelaxedMockK
    private lateinit var networkHandler: NetworkHandler

    @RelaxedMockK
    private lateinit var service: StarWarApiImpl

    @RelaxedMockK
    private lateinit var speciesResponseCall: Call<SpeciesResponseEntity>

    @RelaxedMockK
    private lateinit var filmResponseCall: Call<FilmResponseEntity>

    @RelaxedMockK
    private lateinit var planetListResponseCall: Call<PlanetListResponseEntity>

    @RelaxedMockK
    private lateinit var peopleListResponseCall: Call<PeopleListResponseEntity>

    @RelaxedMockK
    private lateinit var speciesResponse: Response<SpeciesResponseEntity>

    @RelaxedMockK
    private lateinit var filmResponse: Response<FilmResponseEntity>

    @RelaxedMockK
    private lateinit var planetListResponse: Response<PlanetListResponseEntity>

    @RelaxedMockK
    private lateinit var peopleListResponse: Response<PeopleListResponseEntity>


    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)

        speciesResponseEntity = SpeciesResponseEntity(speciesName)

        peopleEntity = PeopleEntity(peopleName)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))

        filmResponseEntity = FilmResponseEntity(filmName)

        planetEntity = PlanetListEntity(planetName)
        planetListEntity = PlanetListResponseEntity(null, null, null, listOf(planetEntity))
    }

    @Test
    fun `should return empty people list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns peopleListEntity
        every { peopleListResponse.isSuccessful } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(peopleName) } returns peopleListResponseCall

        val peopleEntity = networkRepository.getPeopleByQuery(peopleName)

        peopleEntity shouldEqual Right(peopleListEntity.toPeopleList())
        verify(exactly = 1) { service.getPeopleListByQuery(peopleName) }
    }

    @Test
    fun `should return empty planet list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns planetListEntity
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val planetEntity = networkRepository.getPlanetsByQuery(peopleName)

        planetEntity shouldEqual Right(planetListEntity.toPlanetsDataModel())
        verify(exactly = 1) { service.getPlanetListByQuery(peopleName) }
    }

    @Test
    fun `should return empty film list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.body() } returns filmResponseEntity
        every { filmResponse.isSuccessful } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(filmId) } returns filmResponseCall

        val planetEntity = networkRepository.getFilmByQuery(filmId)

        planetEntity shouldEqual Right(filmResponseEntity.toFilmsDataModel())
        verify(exactly = 1) { service.getFilmByQuery(filmId) }
    }

    @Test
    fun `should return empty species list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns speciesResponseEntity
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        val speciesEntity = networkRepository.getSpeciesByQuery(speciesId)

        speciesEntity shouldEqual Right(speciesResponseEntity.toSpeciesDataModel())
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
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns speciesResponseEntity
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(speciesId) } returns speciesResponseCall

        // Act
        val speciesResponseEntity = networkRepository.getSpeciesByQuery(speciesId)

        //Verify
        speciesResponseEntity.onSuccess { it.name shouldEqual speciesName }
        verify(exactly = 1) { service.getSpeciesByQuery(speciesId) }
    }


    @Test
    fun `should get planet list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns planetListEntity
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(peopleName) } returns planetListResponseCall

        val peopleListResponseEntity = networkRepository.getPlanetsByQuery(peopleName)

        peopleListResponseEntity.onSuccess {
            it.results.get(0).name shouldEqual planetName
        }

        verify(exactly = 1) { service.getPlanetListByQuery(peopleName) }
    }


    @Test
    fun `should get people list from api`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns peopleListEntity
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
        every { filmResponse.body() } returns filmResponseEntity
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