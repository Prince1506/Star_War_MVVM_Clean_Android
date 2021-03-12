package com.mvvm_clean.user_details.features.people_details.domain.use_cases

import com.mvvm_clean.user_details.UnitTest
import com.mvvm_clean.user_details.core.domain.functional.Either
import com.mvvm_clean.user_details.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.user_details.features.people_details.domain.models.PlanetListDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private val PLANET_ID = 1

class GetLoginInfoTest : UnitTest() {

    // Late int Variables -----------------
    private lateinit var getLoginInfo: GetLoginInfo

    // Annotations Variables -----------------
    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        getLoginInfo = GetLoginInfo(starWarApiRepository)
        every { starWarApiRepository.getLoginInfo(PLANET_ID) } returns Either.Right(
            PlanetListDataModel.empty
        )
    }

    // Test Cases---------------------------------------------

    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getLoginInfo.run(PLANET_ID) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getLoginInfo(PLANET_ID) }
    }
}
