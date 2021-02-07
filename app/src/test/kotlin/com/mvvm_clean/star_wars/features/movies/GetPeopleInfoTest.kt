package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
import com.mvvm_clean.star_wars.features.people_list.domain.api.StarWarApiRepository
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPeopleInfoTest : UnitTest() {

    private lateinit var getPeopleInfo: GetPeopleInfo

    @MockK
    private lateinit var starWarApiRepository: StarWarApiRepository

    @Before
    fun setUp() {
        getPeopleInfo = GetPeopleInfo(starWarApiRepository)
        every { starWarApiRepository.getPeopleByQuery() } returns Right(listOf(PeopleListDataModel.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getPeopleInfo.run(UseCase.None()) }

        verify(exactly = 1) { starWarApiRepository.getPeopleByQuery() }
    }
}
