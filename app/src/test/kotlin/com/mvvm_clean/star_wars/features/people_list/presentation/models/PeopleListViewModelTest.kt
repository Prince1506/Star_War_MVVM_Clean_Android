package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.lifecycle.MutableLiveData
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
        val peopleListDataModel = PeopleListDataModel(
            0,
            null,
            null,
            listOf(peopleEntity)
        )

        coEvery { getPeopleInfo.run(peopleName) } returns Either.Right(peopleListDataModel)

        peopleListViewModel.peopleListLiveData.observeForever {
            it!!.peopleList?.size shouldEqualTo 1
            it.peopleList?.get(1)?.name shouldEqualTo peopleName
        }

        runBlocking { peopleListViewModel.loadPeopleList(peopleName) }

    }

    @Test
    fun `For API failure progress live data should reset`() {
        every { peopleListViewModel.getProgressLoading() } returns MutableLiveData<Boolean>()

        runBlocking { peopleListViewModel.handlePeopleListFailure(null) }

        peopleListViewModel.getIsLoading()?.observeForever {
            it shouldEqualTo false
        }

    }

    @Test
    fun `For API success progress live data should reset`() {

        runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

        peopleListViewModel.getIsLoading()?.observeForever {
            it shouldEqualTo false
        }
    }


    @Test
    fun `Whenever user name searched it should be observed forever`() {
        every {
            peopleListViewModel.getPeopleNameMutabeLiveData()
        } returns MutableLiveData<String>()

        runBlocking { peopleListViewModel.setSearchQueryString(peopleName) }

        peopleListViewModel.peopleNameMutableLiveData.observeForever {
            it shouldEqualTo peopleName
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

        // Assert

        // Act
        runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

        // Verify
        peopleListViewModel.peopleListLiveData.observeForever {
            it!!.peopleList?.size shouldEqualTo 1
            it.peopleList?.get(1)?.name shouldEqualTo peopleName
        }
    }


}