package com.mvvm_clean.star_wars.features.people_list.domain.repo

import com.mvvm_clean.star_wars.core.domain.exception.Failure.FeatureFailure

class PeopleListApiFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentMovie : FeatureFailure()
}

