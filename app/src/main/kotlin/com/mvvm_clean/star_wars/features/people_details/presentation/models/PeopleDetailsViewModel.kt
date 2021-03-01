package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.annotation.RestrictTo
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetFilmNames
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetPlanetsInfo
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetSpeciesInfo
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import javax.inject.Inject

class PeopleDetailsViewModel
@Inject constructor(
    private val getSpeciesInfo: GetSpeciesInfo,
    private val getPlanetsInfo: GetPlanetsInfo,
    private val getFilmNames: GetFilmNames
) : BaseViewModel() {

    private val mPeopleDetailsDataModel = PeopleDetailsDataModel()

    val mPeopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()
    private val mSpeciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val mFilmDataMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()
    private val mPlanetsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    fun getSpeciesMutableLiveData() = mSpeciesMutableLiveData
    fun getFilmDataMutableLiveData() = mFilmDataMutableLiveData
    fun getPlanetsMutableLiveData() = mPlanetsMutableLiveData

    fun updatePeopleDetailWithPeopleInfo(peopleEntity: PeopleEntity) =
        mPeopleDetailsDataModel.apply {
            name = peopleEntity.name
            birthYear = peopleEntity.birth_year
            height = peopleEntity.height
        }


    fun loadFilmData(filmId: Int) =
        getFilmNames(filmId) { it.fold(::handleFailure, ::handleFilmData) }

    fun loadSpeciesData(speciesId: Int) =
        getSpeciesInfo(speciesId) { it.fold(::handleFailure, ::handleSpeciesData) }

    fun loadPlanetData(planetId: Int) =
        getPlanetsInfo(planetId) { it.fold(::handleFailure, ::handlePlanetsData) }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePlanetsData(planetListDataModel: PlanetListDataModel) {
        mPeopleDetailsDataModel.apply {
            homeworld = planetListDataModel.name
            population = planetListDataModel.population
            mPlanetsMutableLiveData.postValue(this)
        }

        mPeopleDetailMediatorLiveData.addSource(mPlanetsMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
        }
    }

    fun handleFilmData(filmDataModel: FilmDataModel) {

        mPeopleDetailsDataModel.let {
            if (it.film?.isEmpty() == false) {
                it.film += filmDataModel.title + " <br> "
            } else {
                it.film = filmDataModel.title
            }
            it.openingCrawl += filmDataModel.title + " : <br> " + filmDataModel.opening_crawl + " <br> <br> "
            mFilmDataMutableLiveData.postValue(it)
        }

        mPeopleDetailMediatorLiveData.apply {
            addSource(mFilmDataMutableLiveData) {
                value = it
            }
            removeSource(mFilmDataMutableLiveData)
        }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handleSpeciesData(speciesListDataModel: SpeciesDataModel) {

        mPeopleDetailsDataModel.let {
            it.speciesName += speciesListDataModel.name + " <br> "
            it.languages += speciesListDataModel.language + " <br> "
            mSpeciesMutableLiveData.postValue(it)
        }

        mPeopleDetailMediatorLiveData.apply {
            addSource(mSpeciesMutableLiveData) {
                value = it
            }
            removeSource(mSpeciesMutableLiveData)
        }
    }

}