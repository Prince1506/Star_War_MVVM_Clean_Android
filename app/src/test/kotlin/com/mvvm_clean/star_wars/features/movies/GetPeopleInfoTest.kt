package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.functional.Either.Right
import com.mvvm_clean.star_wars.core.interactor.UseCase
import com.mvvm_clean.star_wars.features.characters.GetPeopleInfo
import com.mvvm_clean.star_wars.features.characters.MoviesRepository
import com.mvvm_clean.star_wars.features.characters.PeopleListDataModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPeopleInfoTest : UnitTest() {

    private lateinit var getPeopleInfo: GetPeopleInfo

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        getPeopleInfo = GetPeopleInfo(moviesRepository)
        every { moviesRepository.movies() } returns Right(listOf(PeopleListDataModel.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getPeopleInfo.run(UseCase.None()) }

        verify(exactly = 1) { moviesRepository.movies() }
    }
}
