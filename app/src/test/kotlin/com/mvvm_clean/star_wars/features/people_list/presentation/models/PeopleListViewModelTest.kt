package com.mvvm_clean.star_wars.features.people_list.presentation.models

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class PeopleListViewModelTest : AndroidTest() {

    private lateinit var peopleListViewModel: PeopleListViewModel
    private lateinit var peopleEntity: PeopleEntity
    private val peopleName = "Chewbacaa"
    private lateinit var getPeopleInfo: GetPeopleInfo

    @Before
    fun setUp() {
        peopleEntity = PeopleEntity(peopleName)
    }

    @Test
    fun `loading people list should update live data`() {
        val peopleListDataModel = PeopleListDataModel(
            0,
            null,
            null,
            listOf(peopleEntity)
        )

        coEvery { getPeopleInfo.run(any()) } returns Either.Right(peopleListDataModel)

        peopleListViewModel.peopleListLiveData.observeForever {
            it!!.peopleList?.size shouldEqualTo 1
            it.peopleList?.get(1)?.name shouldEqualTo peopleName
        }

        runBlocking { peopleListViewModel.loadPeopleList(peopleName) }
    }
}