package com.mvvm_clean.star_wars.features.people_details.presentation.models

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetFilmNames
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetPlanetsInfo
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetSpeciesInfo
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PlanetListResponseEntity
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class PeopleDetailsViewModelTest : AndroidTest() {

    private val population = "100"
    private lateinit var planetEntity: PlanetListEntity
    private lateinit var speciesResponseEntity: SpeciesResponseEntity
    private lateinit var planetListEntity: PlanetListResponseEntity
    private lateinit var peopleListEntity: PeopleListResponseEntity
    private lateinit var filmResponseEntity: FilmResponseEntity

    private val filmName = "filmName"
    private val planetName = "planetName"
    private val speciesName = "speciesName"
    private val peopleName = "Chewbacaa"
    private val filmId = 2
    private val speciesId = 1

    private lateinit var peopleDetailsViewModel: PeopleDetailsViewModel
    private lateinit var peopleEntity: PeopleEntity

    @MockK
    private lateinit var getSpeciesInfo: GetSpeciesInfo

    @MockK
    private lateinit var getFilmNames: GetFilmNames

    @MockK
    private lateinit var getPlanetInfo: GetPlanetsInfo


    @Before
    fun setUp() {
        peopleEntity = PeopleEntity(peopleName)
        peopleDetailsViewModel = PeopleDetailsViewModel(getSpeciesInfo, getPlanetInfo, getFilmNames)

        speciesResponseEntity = SpeciesResponseEntity(speciesName)

        peopleEntity = PeopleEntity(peopleName)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))

        filmResponseEntity = FilmResponseEntity(filmName)

        planetEntity = PlanetListEntity(planetName)
        planetEntity.population = population
        planetListEntity = PlanetListResponseEntity(null, null, null, listOf(planetEntity))


    }

    @Test
    fun `loading species list should update live data`() {

        coEvery { getSpeciesInfo.run(any()) } returns Either.Right(speciesResponseEntity.toSpeciesDataModel())

        peopleDetailsViewModel.getSpeciesMutableLiveData().observeForever {
            it!!.speciesName shouldEqualTo speciesName
        }

        runBlocking { peopleDetailsViewModel.loadSpeciesData(speciesId) }
    }

    @Test
    fun `loading film list should update live data`() {

        coEvery { getFilmNames.run(any()) } returns Either.Right(filmResponseEntity.toFilmsDataModel())

        peopleDetailsViewModel.getFilmDataMutableLiveData().observeForever {
            it!!.film shouldEqualTo filmName
        }

        runBlocking { peopleDetailsViewModel.loadFilmData(filmId) }
    }

    @Test
    fun `loading planet list should update live data`() {

        // Assert
        coEvery { getPlanetInfo.run(any()) } returns Either.Right(planetListEntity.toPlanetsDataModel())

        // Act
        runBlocking { peopleDetailsViewModel.loadPlanetData(peopleName) }

        // Verify
        peopleDetailsViewModel.getPlanetsMutableLiveData().observeForever {
            it!!.population shouldEqualTo population
        }

    }

    @Test
    fun `verify that passing response to handleFilmData triggers live data`() {
        //Assert

        //Act
        runBlocking { peopleDetailsViewModel.handleFilmData(filmResponseEntity.toFilmsDataModel()) }

        //Verifiy
        peopleDetailsViewModel.getFilmDataMutableLiveData().observeForever {
            it!!.name shouldEqualTo filmName
        }
    }

    @Test
    fun `verify that passing response to handlePlanetsData triggers live data`() {
        //Assert

        //Act
        runBlocking { peopleDetailsViewModel.handlePlanetsData(planetListEntity.toPlanetsDataModel()) }

        //Verifiy
        peopleDetailsViewModel.getPlanetsMutableLiveData().observeForever {
            it!!.population shouldEqualTo population
        }
    }


    @Test
    fun `verify that passing response to handleSpeciesData triggers live data`() {
        //Assert

        //Act
        runBlocking { peopleDetailsViewModel.handleSpeciesData(speciesResponseEntity.toSpeciesDataModel()) }

        //Verifiy
        peopleDetailsViewModel.getSpeciesMutableLiveData().observeForever {
            it!!.speciesName shouldEqualTo speciesName
        }
    }

}