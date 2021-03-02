package com.mvvm_clean.star_wars.features.people_details.domain.use_cases

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private const val SPECIES_ID = 1
class GetSpeciesInfoTest : UnitTest() {

    // Late int Variables -----------------
    private lateinit var getSpeciesInfo: GetSpeciesInfo

    // Annotations Variables -----------------
    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        getSpeciesInfo = GetSpeciesInfo(starWarApiRepository)
        every { starWarApiRepository.getSpeciesByQuery(SPECIES_ID) } returns Either.Right(
            SpeciesDataModel.empty
        )
    }

    // Test Cases---------------------------------------------
    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getSpeciesInfo.run(SPECIES_ID) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getSpeciesByQuery(SPECIES_ID) }
    }
}