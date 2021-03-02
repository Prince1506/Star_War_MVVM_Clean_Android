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

private const val PLANET_ID = 1
private const val FILM_NAME = "filmName"
private const val PLANET_NAME = "planetName"
private const val SPECIES_NAME = "speciesName"
private const val PEOPLE_NAME = "Chewbacaa"
private const val FILM_ID = 2
private const val SPECIES_ID = 1

class StarWarApiRepositoryTest : UnitTest() {

    // Late int Variables -----------------
    private lateinit var speciesResponseEntity: SpeciesResponseEntity
    private lateinit var planetListEntity: PlanetListResponseEntity
    private lateinit var peopleListEntity: PeopleListResponseEntity
    private lateinit var peopleEntity: PeopleEntity
    private lateinit var filmResponseEntity: FilmResponseEntity
    private lateinit var networkRepository: Network

    // Annotations Variables -----------------
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

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)

        speciesResponseEntity = SpeciesResponseEntity(SPECIES_NAME)

        peopleEntity = PeopleEntity(PEOPLE_NAME)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))

        filmResponseEntity = FilmResponseEntity(FILM_NAME)

        planetListEntity = PlanetListResponseEntity(null, null)
    }

    // Test Cases---------------------------------------------
    @Test
    fun `should return empty people list by default`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns peopleListEntity
        every { peopleListResponse.isSuccessful } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(PEOPLE_NAME) } returns peopleListResponseCall

        //Act
        val peopleEntity = networkRepository.getPeopleByQuery(PEOPLE_NAME)

        //Verify
        peopleEntity shouldEqual Right(peopleListEntity.toPeopleList())
        verify(exactly = 1) { service.getPeopleListByQuery(PEOPLE_NAME) }
    }

    @Test
    fun `should return empty planet list by default`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns planetListEntity
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(PLANET_ID) } returns planetListResponseCall

        //Act
        val planetEntity = networkRepository.getPlanetsByQuery(PLANET_ID)

        //Verify
        planetEntity shouldEqual Right(planetListEntity.toPlanetsDataModel())
        verify(exactly = 1) { service.getPlanetListByQuery(PLANET_ID) }
    }

    @Test
    fun `should return empty film list by default`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.body() } returns filmResponseEntity
        every { filmResponse.isSuccessful } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(FILM_ID) } returns filmResponseCall

        //Act
        val planetEntity = networkRepository.getFilmByQuery(FILM_ID)

        //Verify
        planetEntity shouldEqual Right(filmResponseEntity.toFilmsDataModel())
        verify(exactly = 1) { service.getFilmByQuery(FILM_ID) }
    }

    @Test
    fun `should return empty species list by default`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns speciesResponseEntity
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(SPECIES_ID) } returns speciesResponseCall

        //Act
        val speciesEntity = networkRepository.getSpeciesByQuery(SPECIES_ID)

        //Verify
        speciesEntity shouldEqual Right(speciesResponseEntity.toSpeciesDataModel())
        verify(exactly = 1) { service.getSpeciesByQuery(SPECIES_ID) }
    }


    @Test
    fun `people list api should return network failure when no connection`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns false

        //Act
        val peopleEntity = networkRepository.getPeopleByQuery(PEOPLE_NAME)
        //Verify
        peopleEntity shouldBeInstanceOf Either::class.java
        peopleEntity.isLeft shouldEqual true
        peopleEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `species list api should return network failure when no connection`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns false

        //Act
        val speciesEntity = networkRepository.getSpeciesByQuery(SPECIES_ID)

        //Verify
        speciesEntity shouldBeInstanceOf Either::class.java
        speciesEntity.isLeft shouldEqual true
        speciesEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {}
        )

        verify { service wasNot Called }
    }

    @Test
    fun `planet api should return network failure when no connection`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns false
        //Act
        val planetEntity = networkRepository.getPlanetsByQuery(PLANET_ID)
        //Verify
        planetEntity shouldBeInstanceOf Either::class.java
        planetEntity.isLeft shouldEqual true
        planetEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `film api should return network failure when no connection`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns false
        //Act
        val filmEntity = networkRepository.getFilmByQuery(FILM_ID)
        //Verify
        filmEntity shouldBeInstanceOf Either::class.java
        filmEntity.isLeft shouldEqual true
        filmEntity.fold(
            { failure -> failure shouldBeInstanceOf NetworkConnection::class.java },
            {})
        verify { service wasNot Called }
    }

    @Test
    fun `people list api should return server error if no successful response`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.isSuccessful } returns false
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(PEOPLE_NAME) } returns peopleListResponseCall
        //Act
        val people = networkRepository.getPeopleByQuery(PEOPLE_NAME)
        //Verify
        people shouldBeInstanceOf Either::class.java
        people.isLeft shouldEqual true
        people.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `species list api should return server error if no successful response`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.isSuccessful } returns false
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(SPECIES_ID) } returns speciesResponseCall
        //Act
        val species = networkRepository.getSpeciesByQuery(SPECIES_ID)
        //Verify
        species shouldBeInstanceOf Either::class.java
        species.isLeft shouldEqual true
        species.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `film list api should return server error if no successful response`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.isSuccessful } returns false
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(FILM_ID) } returns filmResponseCall
        //Act
        val films = networkRepository.getFilmByQuery(FILM_ID)
        //Verify
        films shouldBeInstanceOf Either::class.java
        films.isLeft shouldEqual true
        films.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `planet list api should return server error if no successful response`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.isSuccessful } returns false
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(PLANET_ID) } returns planetListResponseCall
        //Act
        val planets = networkRepository.getPlanetsByQuery(PLANET_ID)
        //Verify
        planets shouldBeInstanceOf Either::class.java
        planets.isLeft shouldEqual true
        planets.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }


    @Test
    fun `people api request should catch exceptions`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(PEOPLE_NAME) } returns peopleListResponseCall
        //Act
        val peopleList = networkRepository.getPeopleByQuery(PEOPLE_NAME)
        //Verify
        peopleList shouldBeInstanceOf Either::class.java
        peopleList.isLeft shouldEqual true
        peopleList.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `planet api request should catch exceptions`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(PLANET_ID) } returns planetListResponseCall
        //Act
        val planets = networkRepository.getPlanetsByQuery(PLANET_ID)
        //Verify
        planets shouldBeInstanceOf Either::class.java
        planets.isLeft shouldEqual true
        planets.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `film api request should catch exceptions`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(FILM_ID) } returns filmResponseCall
        //Act
        val films = networkRepository.getFilmByQuery(FILM_ID)
        //Verify
        films shouldBeInstanceOf Either::class.java
        films.isLeft shouldEqual true
        films.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `species api request should catch exceptions`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(SPECIES_ID) } returns speciesResponseCall
        //Act
        val species = networkRepository.getSpeciesByQuery(SPECIES_ID)
        //Verify
        species shouldBeInstanceOf Either::class.java
        species.isLeft shouldEqual true
        species.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }


    @Test
    fun `should get species list from api`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { speciesResponse.body() } returns speciesResponseEntity
        every { speciesResponse.isSuccessful } returns true
        every { speciesResponseCall.execute() } returns speciesResponse
        every { service.getSpeciesByQuery(SPECIES_ID) } returns speciesResponseCall

        // Act
        val speciesResponseEntity = networkRepository.getSpeciesByQuery(SPECIES_ID)

        //Verify
        speciesResponseEntity.onSuccess { it.name shouldEqual SPECIES_NAME }
        verify(exactly = 1) { service.getSpeciesByQuery(SPECIES_ID) }
    }


    @Test
    fun `should get planet list from api`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { planetListResponse.body() } returns planetListEntity
        every { planetListResponse.isSuccessful } returns true
        every { planetListResponseCall.execute() } returns planetListResponse
        every { service.getPlanetListByQuery(PLANET_ID) } returns planetListResponseCall
        //Act
        val peopleListResponseEntity = networkRepository.getPlanetsByQuery(PLANET_ID)
        //Verify
        peopleListResponseEntity.onSuccess {
            it.name shouldEqual PLANET_NAME
        }

        verify(exactly = 1) { service.getPlanetListByQuery(PLANET_ID) }
    }


    @Test
    fun `should get people list from api`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { peopleListResponse.body() } returns peopleListEntity
        every { peopleListResponse.isSuccessful } returns true
        every { peopleListResponseCall.execute() } returns peopleListResponse
        every { service.getPeopleListByQuery(PEOPLE_NAME) } returns peopleListResponseCall
        //Act
        val peopleListResponseEntity = networkRepository.getPeopleByQuery(PEOPLE_NAME)
        //Verify
        peopleListResponseEntity.onSuccess {
            it.people?.get(0)?.name shouldEqual PEOPLE_NAME
        }

        verify(exactly = 1) { service.getPeopleListByQuery(PEOPLE_NAME) }
    }


    @Test
    fun `should get film list from api`() {
        //Assert
        every { networkHandler.isNetworkAvailable() } returns true
        every { filmResponse.body() } returns filmResponseEntity
        every { filmResponse.isSuccessful } returns true
        every { filmResponseCall.execute() } returns filmResponse
        every { service.getFilmByQuery(FILM_ID) } returns filmResponseCall

        //Act
        val filmResponseEntity = networkRepository.getFilmByQuery(FILM_ID)
        //Verify
        filmResponseEntity.onSuccess {
            it.title shouldEqual FILM_NAME
        }
        verify(exactly = 1) { service.getFilmByQuery(FILM_ID) }
    }

}