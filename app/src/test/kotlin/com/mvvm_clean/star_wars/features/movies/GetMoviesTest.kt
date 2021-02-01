package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.UnitTest
import com.mvvm_clean.star_wars.core.functional.Either.Right
import com.mvvm_clean.star_wars.core.interactor.UseCase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesTest : UnitTest() {

    private lateinit var getMovies: GetMovies

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        getMovies = GetMovies(moviesRepository)
        every { moviesRepository.movies() } returns Right(listOf(Movie.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getMovies.run(UseCase.None()) }

        verify(exactly = 1) { moviesRepository.movies() }
    }
}
