package com.mvvm_clean.star_wars.features.common.domain.models

import com.mvvm_clean.star_wars.core.domain.exception.Failure.FeatureFailure

class ApiFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentPeopleList : FeatureFailure()
}

