package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.functional.Either.Right
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest : AndroidTest() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @MockK
    private lateinit var getMovieDetails: GetMovieDetails

    @MockK
    private lateinit var playMovie: PlayMovie

    @Before
    fun setUp() {
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetails, playMovie)
    }

    @Test
    fun `loading movie details should update live data`() {
        val movieDetails = MovieDetails(
            0, "IronMan", "poster", "summary",
            "cast", "director", 2018, "trailer"
        )
        coEvery { getMovieDetails.run(any()) } returns Right(movieDetails)

        movieDetailsViewModel.movieDetails.observeForever {
            with(it!!) {
                id shouldEqualTo 0
                title shouldEqualTo "IronMan"
                poster shouldEqualTo "poster"
                summary shouldEqualTo "summary"
                cast shouldEqualTo "cast"
                director shouldEqualTo "director"
                year shouldEqualTo 2018
                trailer shouldEqualTo "trailer"
            }
        }

        runBlocking { movieDetailsViewModel.loadMovieDetails(0) }
    }
}