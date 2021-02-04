package com.mvvm_clean.star_wars.core.domain.interactor

import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.domain.functional.Either.Right
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Test

class UseCaseTest : AndroidTest() {

    private val useCase = MyUseCase()

    @Test
    fun `running use case should return 'Either' of use case type`() {
        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }

        result shouldEqual Right(MyType(TYPE_TEST))
    }

//    @Test fun `should return correct data when executing use case`() {
//        var result: Either<Failure, MyType>? = null
//
//        val params = MyParams("TestParam")
//        val onResult = { myResult: Either<Failure, MyType> -> result = myResult }
//
//        runBlocking { useCase(params, onResult) }
//
//        result shouldEqual Right(MyType(TYPE_TEST))
//    }

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>() {
        override suspend fun run(params: MyParams) = Right(MyType(TYPE_TEST))
    }

    companion object {
        private const val TYPE_TEST = "Test"
        private const val TYPE_PARAM = "ParamTest"
    }
}
