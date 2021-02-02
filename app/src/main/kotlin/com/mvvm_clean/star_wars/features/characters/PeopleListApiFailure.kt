package com.mvvm_clean.star_wars.features.characters

import com.mvvm_clean.star_wars.core.exception.Failure.FeatureFailure

class PeopleListApiFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentMovie : FeatureFailure()
}

