package com.mvvm_clean.star_wars.features.people_details.domain.models

import android.text.Html
import androidx.core.text.HtmlCompat
import com.mvvm_clean.star_wars.core.domain.extension.dash
import com.mvvm_clean.star_wars.core.domain.extension.empty
import com.mvvm_clean.star_wars.core.domain.extension.getUnitCM
import com.mvvm_clean.star_wars.core.domain.extension.isEmptyOrNull

/**
 * All data variables are nullable because API can return null values too.
 * Here variables are initialised by default to empty or dash according to
 * what we need to show on screen when they are null.
 *
 * Data models are the class which can contain manipulation according to
 * business needs.
 */
data class PeopleDetailsDataModel(
    var speciesName: String? = String.empty(),
    var birthYear: String? = String.dash(),
    var height: String? = String.dash(),
    var name: String? = String.dash(),
    var languages: String? = String.empty(),
    var homeworld: String? = String.empty(),
    var population: String? = String.empty(),
    var film: String? = String.empty(),
    var openingCrawl: String? = String.empty(),
) {
    val speciesNameNotNull: String?
        get() =
            if (String.isEmptyOrNull(speciesName))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(speciesName)

    private fun parseHtmlFromStr(str: String?) =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(str, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
        } else {
            Html.fromHtml(str).toString()
        }

    val birthYearNotNull: String?
        get() =
            if (String.isEmptyOrNull(birthYear))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(birthYear)

    val nameNotNull: String?
        get() =
            if (String.isEmptyOrNull(name))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(name)

    val languagesNotNull: String?
        get() =
            if (String.isEmptyOrNull(languages))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(languages)


    val heightNotNull: String?
        get() =
            if (String.isEmptyOrNull(height))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(height + String.getUnitCM())

    val homeworldNotNull: String?
        get() =
            if (String.isEmptyOrNull(homeworld))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(homeworld)


    val populationNotNull: String?
        get() =
            if (String.isEmptyOrNull(population))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(population)


    val filmNotNull: String?
        get() =
            if (String.isEmptyOrNull(film))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(film)


    val openingCrawlNotNull: String?
        get() =
            if (String.isEmptyOrNull(openingCrawl))
                parseHtmlFromStr(String.dash())
            else
                parseHtmlFromStr(openingCrawl)


    fun isEmpty() =
        String.isEmptyOrNull(speciesName) &&
                String.isEmptyOrNull(birthYear) &&
                String.isEmptyOrNull(height) &&
                String.isEmptyOrNull(name) &&
                String.isEmptyOrNull(languages) &&
                String.isEmptyOrNull(homeworld) &&
                String.isEmptyOrNull(population) &&
                String.isEmptyOrNull(film) &&
                String.isEmptyOrNull(openingCrawl)
}