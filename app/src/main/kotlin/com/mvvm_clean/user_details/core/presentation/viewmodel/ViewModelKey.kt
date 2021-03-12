package com.mvvm_clean.user_details.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Maintains a Map of all view modules created.
 */
@MapKey
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)