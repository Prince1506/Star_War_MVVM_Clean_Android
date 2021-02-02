package com.mvvm_clean.star_wars.features.characters

import android.content.Context
import com.mvvm_clean.star_wars.core.exception.Failure
import com.mvvm_clean.star_wars.core.functional.Either
import com.mvvm_clean.star_wars.core.functional.Either.Right
import com.mvvm_clean.star_wars.core.interactor.UseCase
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import com.mvvm_clean.star_wars.core.navigation.Navigator
import com.mvvm_clean.star_wars.features.characters.PlayMovie.Params
import javax.inject.Inject

class PlayMovie
@Inject constructor(
    private val context: Context,
    private val navigator: Navigator
) : UseCase<None, Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        navigator.openVideo(context, params.url)
        return Right(None())
    }

    data class Params(val url: String)
}
