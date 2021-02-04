//package com.mvvm_clean.star_wars.features.characters
//
//import com.mvvm_clean.star_wars.core.domain.interactor.UseCase
//import com.mvvm_clean.star_wars.features.characters.GetPeopleDetails.Params
//import javax.inject.Inject
//
//class GetPeopleDetails
//@Inject constructor(private val starWarApiRepository: StarWarApiRepository) :
//    UseCase<PeopleDetailsDataModel, Params>() {
//
//    override suspend fun run(params: Params) = starWarApiRepository.movieDetails(params.id)
//
//    data class Params(val id: String)
//}
