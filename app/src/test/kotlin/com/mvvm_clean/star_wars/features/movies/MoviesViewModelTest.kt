package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.functional.Either.Right
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest : AndroidTest() {

    private lateinit var moviesViewModel: MoviesViewModel

    @MockK
    private lateinit var getMovies: GetMovies

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(getMovies)
    }

    @Test
    fun `loading movies should update live data`() {
        val moviesList = listOf(Movie(0, "IronMan"), Movie(1, "Batman"))
        coEvery { getMovies.run(any()) } returns Right(moviesList)

        moviesViewModel.movies.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].poster shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].poster shouldEqualTo "Batman"
        }

        runBlocking { moviesViewModel.loadMovies() }
    }
}