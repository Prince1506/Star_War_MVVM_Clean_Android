package com.mvvm_clean.star_wars.features.people_list.domain.models

import com.mvvm_clean.star_wars.AndroidTest

class PeopleListDataModelDetailsViewModelTest : AndroidTest() {

//    private lateinit var peopleDetailsViewModel: PeopleDetailsViewModel
//
//    @MockK
//    private lateinit var getPeopleDetails: GetPeopleDetails
//
//
//    @Before
//    fun setUp() {
////        peopleDetailsViewModel = PeopleDetailsViewModel(getPeopleDetails, playMovie)
//    }
//
//    @Test
//    fun `loading movie details should update live data`() {
//        val movieDetails = PeopleDetailsDataModel(
//            0, "IronMan", "poster", "summary",
//            "cast", "director", 2018, "trailer"
//        )
//        coEvery { getPeopleDetails.run(any()) } returns Right(movieDetails)
//
//        peopleDetailsViewModel.peopleDetailsLiveData.observeForever {
//            with(it!!) {
//                id shouldEqualTo 0
//                title shouldEqualTo "IronMan"
//                poster shouldEqualTo "poster"
//                summary shouldEqualTo "summary"
//                cast shouldEqualTo "cast"
//                director shouldEqualTo "director"
//                year shouldEqualTo 2018
//                trailer shouldEqualTo "trailer"
//            }
//        }
//
////        runBlocking { peopleDetailsViewModel.loadPeopleDetails(0) }
//    }
}