package com.mvvm_clean.star_wars.features.people_list.domain.use_cases

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.common.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPeopleInfoTest : UnitTest() {
    private val peopleName = "peopleName"
    private lateinit var getPeopleInfo: GetPeopleInfo

    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    @Before
    fun setUp() {
        getPeopleInfo = GetPeopleInfo(starWarApiRepository)
        every { starWarApiRepository.getPeopleByQuery(peopleName) } returns Either.Right(
            PeopleListDataModel.empty
        )
    }

    /**
     * Check that API method is called once to get fact list
     */
    @Test
    fun `should get data from repository`() {

        // Act
        runBlocking { getPeopleInfo.run(peopleName) }

        // Verify
        verify(exactly = 1) { starWarApiRepository.getPeopleByQuery(peopleName) }
    }
}
