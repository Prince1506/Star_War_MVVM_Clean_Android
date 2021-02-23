package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFilmNamesTest : UnitTest() {
    private val filmId = 1
    private lateinit var getFilmNames: GetFilmNames

    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    @Before
    fun setUp() {
        getFilmNames = GetFilmNames(starWarApiRepository)
        every { starWarApiRepository.getFilmByQuery(filmId) } returns Either.Right(
            FilmDataModel.empty
        )
    }

    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getFilmNames.run(filmId) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getFilmByQuery(filmId) }
    }
}
