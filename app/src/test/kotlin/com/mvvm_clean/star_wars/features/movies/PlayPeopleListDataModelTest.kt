package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.navigation.Navigator
import com.mvvm_clean.star_wars.features.characters.PlayMovie
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class PlayPeopleListDataModelTest : AndroidTest() {

    private lateinit var playMovie: PlayMovie

    private val context = context()

    @MockK
    private lateinit var navigator: Navigator

    @Before
    fun setUp() {
        playMovie = PlayMovie(context, navigator)
    }

    @Test
    fun `should play movie trailer`() {
        val params = PlayMovie.Params(Companion.VIDEO_URL)

        playMovie(params)

        verify(exactly = 1) { navigator.openVideo(context, VIDEO_URL) }
    }

    companion object {
        private const val VIDEO_URL = "https://www.youtube.com/watch?v=fernando"
    }
}
