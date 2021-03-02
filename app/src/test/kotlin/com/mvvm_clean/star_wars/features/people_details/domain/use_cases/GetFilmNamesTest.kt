package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private const val FILM_ID = 1

class GetFilmNamesTest : UnitTest() {

    // Late int Variables -----------------
    private lateinit var getFilmNames: GetFilmNames

    // Annotations Variables -----------------
    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        getFilmNames = GetFilmNames(starWarApiRepository)
        every { starWarApiRepository.getFilmByQuery(FILM_ID) } returns Either.Right(
            FilmDataModel.empty
        )
    }

    // Test Cases---------------------------------------------
    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getFilmNames.run(FILM_ID) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getFilmByQuery(FILM_ID) }
    }
}
