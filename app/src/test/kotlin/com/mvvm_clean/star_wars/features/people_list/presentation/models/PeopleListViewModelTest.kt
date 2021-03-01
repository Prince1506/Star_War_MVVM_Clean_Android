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
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PeopleListViewModelTest {

    private val progressDataObserver = Observer<Boolean?> {
        it shouldEqualTo false
    }

    private val peopleNameObserver = Observer<String>
    {
        it shouldEqualTo peopleName
    }
    private val peopleListObserver = Observer<PeopleListView> {
        it!!.peopleList?.size shouldEqualTo 1
        it.peopleList?.get(1)?.name shouldEqualTo peopleName
    }

    @RelaxedMockK
    private lateinit var peopleListViewModel: PeopleListViewModel


    private lateinit var peopleEntity: PeopleEntity
    private val peopleName = "Chewbacaa"
    private lateinit var peopleListEntity: PeopleListResponseEntity

    @MockK
    private lateinit var getPeopleInfo: GetPeopleInfo


    companion object {

        @BeforeClass
        fun setupBeforeClass() {

        }

    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        peopleEntity = PeopleEntity(peopleName)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))
    }

    @After
    fun method() {
    }


    @Test
    fun `loading people list should update live data`() {
        try {
            val peopleListDataModel = PeopleListDataModel(
                0,
                null,
                null,
                listOf(peopleEntity)
            )

            coEvery { getPeopleInfo.run(peopleName) } returns Either.Right(peopleListDataModel)

            peopleListViewModel.getPeopleListLiveData().observeForever(peopleListObserver)

            runBlocking { peopleListViewModel.loadPeopleList(peopleName) }
        } finally {
            peopleListViewModel.getPeopleListLiveData().removeObserver(peopleListObserver)
        }
    }

    @Test
    fun `For API failure progress live data should reset`() {
        try {
            every { peopleListViewModel.getProgressLoadingMutableLiveData() } returns MutableLiveData<Boolean>()

            runBlocking { peopleListViewModel.handlePeopleListFailure(null) }

            peopleListViewModel.getProgressLoadingLiveData().observeForever(progressDataObserver)

        } finally {
            peopleListViewModel.getProgressLoadingLiveData().removeObserver(progressDataObserver)

        }

    }

    @Test
    fun `For API success progress live data should reset`() {
        try {

            runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

            peopleListViewModel.getProgressLoadingLiveData().observeForever(progressDataObserver)
        } finally {
            peopleListViewModel.getProgressLoadingLiveData().removeObserver(progressDataObserver)

        }
    }


    @Test
    fun `Whenever user name searched it should be observed forever`() {
        try {

            every {
                peopleListViewModel.getPeopleNameMutabeLiveData()
            } returns MutableLiveData<String>()

            runBlocking { peopleListViewModel.setSearchQueryString(peopleName) }

            peopleListViewModel.getPeopleNameMutabeLiveData().observeForever(peopleNameObserver)
        } finally {
            peopleListViewModel.getPeopleNameMutabeLiveData().removeObserver(peopleNameObserver)
        }
    }


    @Test
    fun `User Name observing forever should get cleared`() {
        every {
            peopleListViewModel.getPeopleNameMutabeLiveData()
        } returns MutableLiveData<String>()

        every {
            peopleListViewModel.getPeopleNameMutabeLiveData().removeObserver(any())
        } returns Unit

        peopleListViewModel.onCleared()

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