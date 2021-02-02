package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.functional.Either.Right
import com.mvvm_clean.star_wars.features.characters.GetPeopleInfo
import com.mvvm_clean.star_wars.features.characters.PeopleListDataModel
import com.mvvm_clean.star_wars.features.characters.PeopleListViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PeopleListViewModelTest : AndroidTest() {

    private lateinit var peopleListViewModel: PeopleListViewModel

    @MockK
    private lateinit var getPeopleInfo: GetPeopleInfo

    @Before
    fun setUp() {
        peopleListViewModel = PeopleListViewModel(getPeopleInfo)
    }

    @Test
    fun `loading movies should update live data`() {
        val moviesList = listOf(PeopleListDataModel(0, "IronMan"), PeopleListDataModel(1, "Batman"))
        coEvery { getPeopleInfo.run(any()) } returns Right(moviesList)

        peopleListViewModel.peopleListLiveData.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].poster shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].poster shouldEqualTo "Batman"
        }

        runBlocking { peopleListViewModel.loadPeopleList() }
    }
}