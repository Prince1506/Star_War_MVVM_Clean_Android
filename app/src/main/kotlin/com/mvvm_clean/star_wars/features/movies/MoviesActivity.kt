package com.mvvm_clean.star_wars.features.movies

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.platform.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
