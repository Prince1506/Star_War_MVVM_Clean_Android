package com.mvvm_clean.star_wars.core.base

import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.AndroidTest
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class BaseViewModelTest : AndroidTest() {

    @Test
    fun `should handle failure by updating live data`() {
        MyViewModel().apply {

            //Act
            handleError(Failure.NetworkConnection)
            //Verify
            getFailureLiveData() shouldBeInstanceOf MutableLiveData::class.java
            getFailureLiveData().value shouldBeInstanceOf Failure.NetworkConnection::class.java
        }

    }

    private class MyViewModel : BaseViewModel() {
        fun handleError(failure: Failure) = handleFailure(failure)
    }
}