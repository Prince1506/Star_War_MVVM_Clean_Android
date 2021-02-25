package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.core.domain.functional.Either
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleListResponseEntity
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PeopleListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Boolean?>

    @MockK
    private lateinit var observerStr: Observer<String>

    @MockK
    private lateinit var observerBool: Observer<Boolean>

    @MockK
    private lateinit var observerPeopleList: Observer<PeopleListDataModel>

    @MockK
    private lateinit var failure: Failure

    private lateinit var peopleListEntity: PeopleListResponseEntity

    @RelaxedMockK
    private lateinit var peopleListViewModel: PeopleListViewModel

    @Mock
    private lateinit var peopleListViewModelMock: PeopleListViewModel


    private lateinit var peopleEntity: PeopleEntity
    private val peopleName = "Chewbacaa"

    @RelaxedMockK
    private lateinit var peopleNameMutableLiveData: MutableLiveData<String>

    @Mock
    private lateinit var progressDialog: MutableLiveData<Boolean>

    @MockK
    private lateinit var getPeopleInfo: GetPeopleInfo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        peopleEntity = PeopleEntity(peopleName)
        peopleListEntity = PeopleListResponseEntity(null, null, null, listOf(peopleEntity))
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
            it!!.people?.size shouldEqualTo 1
            it.people?.get(1)?.name shouldEqualTo peopleName
        }

        runBlocking { peopleListViewModel.loadPeopleList(peopleName) }
    }

    @Test
    fun `For API failure progress live data should reset`() {
        var isProgressLoading = MutableLiveData<Boolean>()

        isProgressLoading.postValue(false)

        Mockito.`when`(
            peopleListViewModelMock.isProgressLoading
        ).thenReturn(isProgressLoading)

        peopleListViewModelMock.handlePeopleListFailure(
            null
        )


        Mockito.verify(
            peopleListViewModelMock,
            Mockito.times(1)
        ).isProgressLoading/*.postValue(false).postValue(false) .postValue(argumentCaptor.capture())*/

/*      peopleListViewModel.getIsLoading()?.observeForever {
          it shouldEqualTo false
      }
*/
        /*val liveData: LiveData<Boolean?>? = peopleListViewModel.getIsLoading()

        TestObserver.test<Boolean>(liveData)
            .assertHasValue()
            .assertHistorySize(1)
            .doOnChanged(Consumer { Assert.assertEquals(it, false) })
*/

        /* val values = argumentCaptor.getAllValues();
         Assert.assertEquals(false,values.get(0))*/
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

        runBlocking { peopleListViewModel.setSearchQueryString(peopleName) }

        peopleListViewModel.peopleNameMutableLiveData.observeForever {
            it shouldEqualTo peopleName
        }
    }
/*

    @Test
    fun `User Name observing forever should get cleared`() {
        every { peopleListViewModel.peopleNameMutableLiveData.removeObserver(observer) } just  Runs

        peopleListViewModel.onCleared()

        verify (atLeast = 1) {
            peopleNameMutableLiveData.removeObserver(observer)
        }
    }
*/


    /**
     * Check that the response we got is passed to live data
     */
    @Test
    fun `correct response is passed to loadPeopleList method`() {

        // Assert
        // Act
        runBlocking { peopleListViewModel.handlePeopleList(peopleListEntity.toPeopleList()) }

        // Verify
        peopleListViewModel.peopleListLiveData.observeForever {
            it!!.people?.size shouldEqualTo 1
            it.people?.get(1)?.name shouldEqualTo peopleName
        }


//        peopleListViewModel.peopleListMutableLiveData.value shouldEqual peopleListEntity.toPeopleList()

    }


}