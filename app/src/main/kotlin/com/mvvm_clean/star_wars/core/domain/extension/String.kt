package com.mvvm_clean.star_wars.core.domain.extension

fun String.Companion.empty() = ""

fun String.Companion.isEmptyOrNull(mString: String?): Boolean =
    mString == null || mString.isEmpty()