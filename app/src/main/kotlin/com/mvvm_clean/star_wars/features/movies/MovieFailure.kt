package com.mvvm_clean.star_wars.features.movies

import com.mvvm_clean.star_wars.core.exception.Failure.FeatureFailure

class MovieFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentMovie : FeatureFailure()
}

