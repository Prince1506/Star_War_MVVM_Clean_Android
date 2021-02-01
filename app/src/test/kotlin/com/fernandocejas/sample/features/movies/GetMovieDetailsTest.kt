package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.UnitTest
import com.fernandocejas.sample.core.functional.Either.Right
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieDetailsTest : UnitTest() {

    private lateinit var getMovieDetails: GetMovieDetails

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        getMovieDetails = GetMovieDetails(moviesRepository)
        every { moviesRepository.movieDetails(MOVIE_ID) } returns Right(MovieDetails.empty)
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getMovieDetails.run(GetMovieDetails.Params(MOVIE_ID)) }

        verify(exactly = 1) { moviesRepository.movieDetails(MOVIE_ID) }
    }

    companion object {
        private const val MOVIE_ID = 1
    }
}
