package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.annotation.RestrictTo
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.core.domain.extension.empty
import com.mvvm_clean.star_wars.core.domain.extension.getForwardSlash
import com.mvvm_clean.star_wars.core.domain.extension.putNewLine
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetFilmNames
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetPlanetsInfo
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetSpeciesInfo
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import javax.inject.Inject

// Constant values for detail screen--------------------------
const val film = "film"
const val species = "species"
const val planets = "planets"

private const val FILM_URL = "http://swapi.dev/api/films/"
private const val SPECIES_URL = "http://swapi.dev/api/species/"
private const val PLANET_URL = "http://swapi.dev/api/planets/"
private const val PLANET_NAME = "(Planet Name)"
class PeopleDetailsViewModel
@Inject constructor(
    private val getSpeciesInfo: GetSpeciesInfo,
    private val getPlanetsInfo: GetPlanetsInfo,
    private val getFilmNames: GetFilmNames
) : BaseViewModel() {

    // Field Variables-----------------------------------------------
    private val peopleDetailsDataModel = PeopleDetailsDataModel()
    private val peopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()
    private val mPeopleEntityMutableLiveData = MutableLiveData<PeopleEntity>()
    private val speciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val filmDataMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()
    private val planetsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    //  Helper Methods-----------------------------------------
    fun getSpeciesMutableLiveData() = speciesMutableLiveData
    fun getFilmDataMutableLiveData() = filmDataMutableLiveData
    fun getPlanetsMutableLiveData() = planetsMutableLiveData
    internal fun getPeopleDetailMediatorLiveData() = peopleDetailMediatorLiveData

    private val peopleEntityObserver = Observer<PeopleEntity> {
        makeApiCalls(it)
    }

    fun updatePeopleDetailWithPeopleInfo(peopleEntity: PeopleEntity) =
        peopleDetailsDataModel.apply {
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
        peopleDetailsDataModel.apply {
            homeworld = planetListDataModel.name + PLANET_NAME
            population = planetListDataModel.population
            planetsMutableLiveData.postValue(this)
        }

        peopleDetailMediatorLiveData.addSource(planetsMutableLiveData) {
            peopleDetailMediatorLiveData.value = it
        }
    }

    fun handleFilmData(filmDataModel: FilmDataModel) {

        updatePeopleDetailWithFilmData(filmDataModel)
        addFilmLiveDataToPeopleMediatorLiveData()
    }

    private fun addFilmLiveDataToPeopleMediatorLiveData() {
        peopleDetailMediatorLiveData.apply {
            addSource(filmDataMutableLiveData) {
                value = it
            }
            removeSource(filmDataMutableLiveData)
        }
    }

    private fun updatePeopleDetailWithFilmData(filmDataModel: FilmDataModel) {

        peopleDetailsDataModel.let {
            if (it.film?.isEmpty() == false) {
                it.film += filmDataModel.title + String.putNewLine()
            } else {
                it.film = filmDataModel.title
            }
            it.openingCrawl += filmDataModel.title +
                    String.putNewLine() +
                    filmDataModel.opening_crawl +
                    String.putNewLine() +
                    String.putNewLine()

            filmDataMutableLiveData.postValue(it)
        }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handleSpeciesData(speciesListDataModel: SpeciesDataModel) {

        updatePeopleDataWithSpeciesData(speciesListDataModel)
        addSpeciesLiveDataToPeopleMediatorLiveData()
    }

    private fun addSpeciesLiveDataToPeopleMediatorLiveData() {
        peopleDetailMediatorLiveData.apply {
            addSource(speciesMutableLiveData) {
                value = it
            }
            removeSource(speciesMutableLiveData)
        }
    }

    private fun updatePeopleDataWithSpeciesData(speciesListDataModel: SpeciesDataModel) {
        peopleDetailsDataModel.let {
            it.speciesName += speciesListDataModel.name + String.putNewLine()
            it.languages += speciesListDataModel.language + String.putNewLine()
            speciesMutableLiveData.postValue(it)
        }
    }

    private fun makeApiCalls(peopleEntity: PeopleEntity) {

        peopleEntity.homeworld?.let { it ->
            getIdFromUrl(it).apply {
                if (this != -1)
                    loadPlanetData(this)
            }
        }


        peopleEntity.let {

            for (film in it.films!!) {
                getIdFromUrl(film).apply {
                    if (this != -1)
                        loadFilmData(this)
                }
            }
            for (species in it.species!!) {
                getIdFromUrl(species).apply {
                    if (this != -1) loadSpeciesData(this)
                }
            }
        }
    }

    private fun getIdFromUrl(url: String): Int {

        val urlArrWithId =
            if (url.contains(film)) {
                url.split(FILM_URL).toTypedArray()
            } else if (url.contains(species)) {
                url.split(SPECIES_URL).toTypedArray()
            } else if (url.contains(planets)) {
                url.split(PLANET_URL).toTypedArray()
            } else {
                arrayOf(String.empty(), String.empty())
            }


        val urlId =
            if (urlArrWithId.size > 1 && urlArrWithId[1].contains(String.getForwardSlash())) {
                convertUrlIdFromStrToInt(urlArrWithId)
            } else {
                -1 // -1 tells that id is not available
            }

        return urlId
    }

    private fun convertUrlIdFromStrToInt(
        urlArrWithId: Array<String>
    ): Int {

        var urlId1 = -1
        val filmIdStr = urlArrWithId[1].split(String.getForwardSlash())[0]
        try {
            urlId1 = filmIdStr.toInt()
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }
        return urlId1
    }

    fun setPeopleEntityObserver(peopleEntity: PeopleEntity) =
        mPeopleEntityMutableLiveData.apply {

            if (!hasActiveObservers()) {
                postValue(peopleEntity)
                observeForever(peopleEntityObserver)
            }


        }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        mPeopleEntityMutableLiveData.removeObserver(peopleEntityObserver)
        super.onCleared()
    }

}
