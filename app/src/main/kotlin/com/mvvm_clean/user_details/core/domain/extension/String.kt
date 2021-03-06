package com.mvvm_clean.user_details.core.domain.extension

fun String.Companion.empty() = ""
fun String.Companion.dash() = "-"
fun String.Companion.getUnitCM() = "cm"
fun String.Companion.putNewLine() = " <br> "
fun String.Companion.getForwardSlash() = "/"
fun String.Companion.isEmptyOrNull(mString: String?): Boolean =
    mString == null || mString.isEmpty()