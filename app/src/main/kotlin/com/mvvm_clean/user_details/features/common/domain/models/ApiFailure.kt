package com.mvvm_clean.user_details.features.common.domain.models

import com.mvvm_clean.user_details.core.domain.exception.Failure.FeatureFailure

class ApiFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentPeopleList : FeatureFailure()
}

