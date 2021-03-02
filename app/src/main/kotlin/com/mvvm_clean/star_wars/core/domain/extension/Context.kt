package com.mvvm_clean.star_wars.core.domain.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.inputMethodManager: InputMethodManager
    get() =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
