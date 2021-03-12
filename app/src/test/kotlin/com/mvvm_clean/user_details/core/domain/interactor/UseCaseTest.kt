package com.mvvm_clean.user_details.core.domain.interactor

import com.mvvm_clean.user_details.AndroidTest
import com.mvvm_clean.user_details.core.domain.functional.Either.Right
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Test


private const val TYPE_TEST = "Test"
private const val TYPE_PARAM = "ParamTest"

class UseCaseTest : AndroidTest() {
    // Field Variables -----------------
    private val useCase = MyUseCase()

    // Test Cases-----------------
    @Test
    fun `running use case should return 'Either' of use case type`() {
        //Assert
        val params = MyParams(TYPE_PARAM)
        //Act
        val result = runBlocking { useCase.run(params) }
        //Verify
        result shouldEqual Right(MyType(TYPE_TEST))
    }

    // Helper Classes ------------------------------
    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>() {
        override suspend fun run(params: MyParams) = Right(MyType(TYPE_TEST))
    }
}
