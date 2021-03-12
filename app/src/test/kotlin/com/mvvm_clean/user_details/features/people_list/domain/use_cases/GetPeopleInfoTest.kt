package com.mvvm_clean.user_details.features.people_list.domain.use_cases

import com.mvvm_clean.user_details.UnitTest
import com.mvvm_clean.user_details.core.domain.functional.Either
import com.mvvm_clean.user_details.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


private const val PEOPLE_NAME = "peopleName"

class GetPeopleInfoTest : UnitTest() {

    // Late int Variables -----------------
    private lateinit var getPeopleInfo: GetPeopleInfo

    // Annotations Variables -----------------
    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        getPeopleInfo = GetPeopleInfo(starWarApiRepository)
        every { starWarApiRepository.getPeopleByQuery(PEOPLE_NAME) } returns Either.Right(
            PeopleListDataModel.empty
        )
    }

    // Test Cases---------------------------------------------
    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getPeopleInfo.run(PEOPLE_NAME) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getPeopleByQuery(PEOPLE_NAME) }
    }
}
