package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
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

    private val planetId = 15
    private val speciesObserver = Observer<PeopleDetailsDataModel> {
        it!!.speciesName shouldEqualTo speciesName
    }
    private val filmObserver = Observer<PeopleDetailsDataModel> {
        it!!.film shouldEqualTo filmName
    }
    private val planetObserver = Observer<PeopleDetailsDataModel> {
        it!!.population shouldEqualTo population
    }

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
        planetListEntity = PlanetListResponseEntity(null, null)


    }

    /**
     * Observes a [LiveData] until the `block` is done executing.
     */
    fun <T> LiveData<T>.observeForTesting(block: () -> Unit): Observer<T> {
        val observer = Observer<T> { }
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
        }
        return observer
    }

    @Test
    fun `loading species list should update live data`() {
        try {
            coEvery { getSpeciesInfo.run(any()) } returns Either.Right(speciesResponseEntity.toSpeciesDataModel())

            runBlocking { peopleDetailsViewModel.loadSpeciesData(speciesId) }

            peopleDetailsViewModel.getSpeciesMutableLiveData().observeForever(speciesObserver)

        } finally {
            peopleDetailsViewModel.getSpeciesMutableLiveData().removeObserver(speciesObserver)
        }
    }

    @Test
    fun `loading film list should update live data`() {
        try {
            coEvery { getFilmNames.run(any()) } returns Either.Right(filmResponseEntity.toFilmsDataModel())

            runBlocking { peopleDetailsViewModel.loadFilmData(filmId) }
            peopleDetailsViewModel.getFilmDataMutableLiveData().observeForever(filmObserver)
        } finally {
            peopleDetailsViewModel.getFilmDataMutableLiveData().removeObserver(filmObserver)
        }
    }

    @Test
    fun `loading planet list should update live data`() {
        try {

            // Assert
            coEvery { getPlanetInfo.run(any()) } returns Either.Right(planetListEntity.toPlanetsDataModel())

            // Act
            runBlocking { peopleDetailsViewModel.loadPlanetData(planetId) }

            // Verify
            peopleDetailsViewModel.getPlanetsMutableLiveData().observeForever(planetObserver)
        } finally {
            peopleDetailsViewModel.getPlanetsMutableLiveData().removeObserver(planetObserver)
        }

    }

    @Test
    fun `verify that passing response to handleFilmData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { peopleDetailsViewModel.handleFilmData(filmResponseEntity.toFilmsDataModel()) }

            //Verifiy
            peopleDetailsViewModel.getFilmDataMutableLiveData().observeForever(filmObserver)
        } finally {
            peopleDetailsViewModel.getFilmDataMutableLiveData().removeObserver(filmObserver)
        }
    }

    @Test
    fun `verify that passing response to handlePlanetsData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { peopleDetailsViewModel.handlePlanetsData(planetListEntity.toPlanetsDataModel()) }

            //Verifiy
            peopleDetailsViewModel.getPlanetsMutableLiveData().observeForever(planetObserver)
        } finally {
            peopleDetailsViewModel.getPlanetsMutableLiveData().removeObserver(planetObserver)
        }
    }


    @Test
    fun `verify that passing response to handleSpeciesData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { peopleDetailsViewModel.handleSpeciesData(speciesResponseEntity.toSpeciesDataModel()) }

            //Verifiy
            peopleDetailsViewModel.getSpeciesMutableLiveData().observeForever(speciesObserver)
        } finally {
            peopleDetailsViewModel.getSpeciesMutableLiveData().removeObserver(speciesObserver)
        }
    }
}