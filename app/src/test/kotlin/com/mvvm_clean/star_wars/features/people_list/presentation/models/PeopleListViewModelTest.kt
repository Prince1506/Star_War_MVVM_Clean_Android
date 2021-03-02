package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

private val PEOPLE_NAME = "Chewbacaa"

@RunWith(MockitoJUnitRunner::class)
class PeopleListViewModelTest {

    // Field Variables ---------------------
    private val progressDataObserver = Observer<Boolean?> { it shouldEqualTo false }
    private val peopleNameObserver = Observer<String> { it shouldEqualTo PEOPLE_NAME }
    private val peopleListObserver = Observer<PeopleListView> {
        it!!.peopleList?.size shouldEqualTo 1
        it.peopleList?.get(1)?.name shouldEqualTo PEOPLE_NAME
    }

    // Annotations Variables -----------------
    @RelaxedMockK
    private lateinit var peopleListViewModel: PeopleListViewModel

    @MockK
    private lateinit var getPeopleInfo: GetPeopleInfo

    // Late int Variables -----------------
    private lateinit var peopleEntity: PeopleEntity
    private lateinit var peopleListEntity: PeopleListResponseEntity

    // Override Methods--------------------------------------
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        peopleEntity = PeopleEntity(PEOPLE_NAME)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))
    }

    // Test Cases---------------------------------------------
    @Test
    fun `loading people list should update live data`() {
        try {

            //Assert
            val peopleListDataModel = PeopleListDataModel(
                0,
                null,
                null,
                listOf(peopleEntity)
            )
            coEvery { getPeopleInfo.run(PEOPLE_NAME) } returns Either.Right(peopleListDataModel)

            //Act
            runBlocking { peopleListViewModel.loadPeopleList(PEOPLE_NAME) }

            //Verify
            peopleListViewModel.getPeopleListLiveData().observeForever(peopleListObserver)

        } finally {
            peopleListViewModel.getPeopleListLiveData().removeObserver(peopleListObserver)
        }
    }

    @Test
    fun `For API failure progress live data should reset`() {
        try {
            //Assert
            every { peopleListViewModel.getProgressLoadingMutableLiveData() } returns MutableLiveData<Boolean>()

            //Act
            runBlocking { peopleListViewModel.handlePeopleListFailure(null) }

            //Verify
            peopleListViewModel.getProgressLoadingLiveData().observeForever(progressDataObserver)

        } finally {
            peopleListViewModel.getProgressLoadingLiveData().removeObserver(progressDataObserver)

        }

    }

    @Test
    fun `For API success progress live data should reset`() {
        try {

            //Act
            runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

            //Verify
            peopleListViewModel.getProgressLoadingLiveData().observeForever(progressDataObserver)
        } finally {
            peopleListViewModel.getProgressLoadingLiveData().removeObserver(progressDataObserver)

        }
    }


    @Test
    fun `Whenever user name searched it should be observed forever`() {
        try {
            //Assert
            every {
                peopleListViewModel.getPeopleNameMutabeLiveData()
            } returns MutableLiveData<String>()

            //Act
            runBlocking { peopleListViewModel.setSearchQueryString(PEOPLE_NAME) }

            //Verify
            peopleListViewModel.getPeopleNameMutabeLiveData().observeForever(peopleNameObserver)
        } finally {
            peopleListViewModel.getPeopleNameMutabeLiveData().removeObserver(peopleNameObserver)
        }
    }


    @Test
    fun `User Name observing forever should get cleared`() {
        //Assert
        every {
            peopleListViewModel.getPeopleNameMutabeLiveData()
        } returns MutableLiveData<String>()

        every {
            peopleListViewModel.getPeopleNameMutabeLiveData().removeObserver(any())
        } returns Unit


        //Act
        peopleListViewModel.onCleared()

        //Verify
        verify(exactly = 1) {
            peopleListViewModel.onCleared()
        }
        confirmVerified(peopleListViewModel)

    }

    /**
     * Check that the response we got is passed to live data
     */
    @Test
    fun `correct response is passed to handlePeopleList method`() {
        try {
            // Assert

            // Act
            runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

            // Verify
            peopleListViewModel.getPeopleListLiveData().observeForever(peopleListObserver)

        } finally {
            peopleListViewModel.getPeopleListLiveData().removeObserver(peopleListObserver)
        }

    }
}