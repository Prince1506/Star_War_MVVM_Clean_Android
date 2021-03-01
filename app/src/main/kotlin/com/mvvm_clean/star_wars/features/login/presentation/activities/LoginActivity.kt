package com.mvvm_clean.star_wars.features.login.presentation.activities

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.base.BaseActivity
import com.mvvm_clean.star_wars.features.login.presentation.fragments.LoginFragment

// Activity to show login screen.
class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()
}
