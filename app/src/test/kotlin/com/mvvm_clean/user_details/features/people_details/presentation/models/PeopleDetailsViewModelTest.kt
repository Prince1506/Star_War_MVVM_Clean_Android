package com.mvvm_clean.user_details.features.people_details.presentation.models

import androidx.lifecycle.Observer
import com.mvvm_clean.user_details.AndroidTest
import com.mvvm_clean.user_details.core.domain.functional.Either
import com.mvvm_clean.user_details.features.people_details.data.repo.response.FilmResponseEntity
import com.mvvm_clean.user_details.features.people_details.data.repo.response.SpeciesResponseEntity
import com.mvvm_clean.user_details.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.user_details.features.people_details.domain.use_cases.GetLoginInfo
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.user_details.features.people_list.data.repo.response.PlanetListResponseEntity
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

/**
 * As per Kotlin documentation in kotlin we need to define constants
 * outside of class
 * https://developer.android.com/kotlin/style-guide#specific_constructs
 */

// Constants
private const val FILM_NAME = "filmName"
private const val SPECIES_NAME = "speciesName"
private const val PEOPLE_NAME = "Chewbacaa"
private const val FILM_ID = 2
private const val SPECIES_ID = 1
private const val POPULATION = "100"
private const val PLANET_ID = 15

class PeopleDetailsViewModelTest : AndroidTest() {

    // Late int Variables -----------------
    private lateinit var mSpeciesResponseEntity: SpeciesResponseEntity
    private lateinit var mPlanetListEntity: PlanetListResponseEntity
    private lateinit var mPeopleListEntity: PeopleListResponseEntity
    private lateinit var mFilmResponseEntity: FilmResponseEntity
    private lateinit var mPeopleDetailsViewModel: PeopleDetailsViewModel
    private lateinit var mPeopleEntity: PeopleEntity

    // Annotations Variables -----------------
    @MockK
    private lateinit var mGetSpeciesInfo: GetSpeciesInfo

    @MockK
    private lateinit var mGetFilmNames: GetFilmNames

    @MockK
    private lateinit var mGetPlanetInfo: GetLoginInfo

    // Field Variables ---------------------
    private val mSpeciesObserver = Observer<PeopleDetailsDataModel> {
        it!!.speciesName shouldEqualTo SPECIES_NAME
    }
    private val mFilmObserver = Observer<PeopleDetailsDataModel> {
        it!!.film shouldEqualTo FILM_NAME
    }
    private val mPlanetObserver = Observer<PeopleDetailsDataModel> {
        it!!.population shouldEqualTo POPULATION
    }

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        mPeopleEntity = PeopleEntity(PEOPLE_NAME)
        mPeopleDetailsViewModel = PeopleDetailsViewModel(
            mGetSpeciesInfo,
            mGetPlanetInfo,
            mGetFilmNames
        )
        mSpeciesResponseEntity = SpeciesResponseEntity(SPECIES_NAME)
        mPeopleListEntity = PeopleListResponseEntity(null, null, null, listOf(mPeopleEntity))
        mFilmResponseEntity = FilmResponseEntity(FILM_NAME)
        mPlanetListEntity = PlanetListResponseEntity(null, null)
    }

    // Test Cases---------------------------------------------
    @Test
    fun `loading species list should update live data`() {
        try {
            //Assert
            coEvery { mGetSpeciesInfo.run(any()) } returns Either.Right(mSpeciesResponseEntity.toSpeciesDataModel())

            //Act
            runBlocking { mPeopleDetailsViewModel.loadSpeciesData(SPECIES_ID) }

            //Verify
            mPeopleDetailsViewModel.getSpeciesMutableLiveData().observeForever(mSpeciesObserver)

        } finally {
            mPeopleDetailsViewModel.getSpeciesMutableLiveData().removeObserver(mSpeciesObserver)
        }
    }

    @Test
    fun `loading film list should update live data`() {
        try {
            //Assert
            coEvery { mGetFilmNames.run(any()) } returns Either.Right(mFilmResponseEntity.toFilmsDataModel())

            //Act
            runBlocking { mPeopleDetailsViewModel.loadFilmData(FILM_ID) }

            //Verify
            mPeopleDetailsViewModel.getFilmDataMutableLiveData().observeForever(mFilmObserver)
        } finally {
            mPeopleDetailsViewModel.getFilmDataMutableLiveData().removeObserver(mFilmObserver)
        }
    }

    @Test
    fun `loading planet list should update live data`() {
        try {

            // Assert
            coEvery { mGetPlanetInfo.run(any()) } returns Either.Right(mPlanetListEntity.toPlanetsDataModel())

            // Act
            runBlocking { mPeopleDetailsViewModel.loadPlanetData(PLANET_ID) }

            // Verify
            mPeopleDetailsViewModel.getPlanetsMutableLiveData().observeForever(mPlanetObserver)
        } finally {
            mPeopleDetailsViewModel.getPlanetsMutableLiveData().removeObserver(mPlanetObserver)
        }

    }

    @Test
    fun `verify that passing response to handleFilmData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { mPeopleDetailsViewModel.handleFilmData(mFilmResponseEntity.toFilmsDataModel()) }

            //Verify
            mPeopleDetailsViewModel.getFilmDataMutableLiveData().observeForever(mFilmObserver)
        } finally {
            mPeopleDetailsViewModel.getFilmDataMutableLiveData().removeObserver(mFilmObserver)
        }
    }

    @Test
    fun `verify that passing response to handlePlanetsData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { mPeopleDetailsViewModel.handlePlanetsData(mPlanetListEntity.toPlanetsDataModel()) }

            //Verify
            mPeopleDetailsViewModel.getPlanetsMutableLiveData().observeForever(mPlanetObserver)
        } finally {
            mPeopleDetailsViewModel.getPlanetsMutableLiveData().removeObserver(mPlanetObserver)
        }
    }


    @Test
    fun `verify that passing response to handleSpeciesData triggers live data`() {
        try {
            //Assert

            //Act
            runBlocking { mPeopleDetailsViewModel.handleSpeciesData(mSpeciesResponseEntity.toSpeciesDataModel()) }

            //Verify
            mPeopleDetailsViewModel.getSpeciesMutableLiveData().observeForever(mSpeciesObserver)
        } finally {
            mPeopleDetailsViewModel.getSpeciesMutableLiveData().removeObserver(mSpeciesObserver)
        }
    }
}