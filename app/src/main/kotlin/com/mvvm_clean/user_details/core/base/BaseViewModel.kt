package com.mvvm_clean.user_details.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm_clean.user_details.core.domain.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {
    // Field Variables ---------------------
    private val failureMutableLiveData: MutableLiveData<Failure> = MutableLiveData()
    private val failureLiveData: LiveData<Failure> = failureMutableLiveData

    //  Helper Methods-----------------------------------------
    internal fun getFailureLiveData() = failureLiveData
    protected fun handleFailure(failure: Failure) {
        failureMutableLiveData.value = failure
    }

}