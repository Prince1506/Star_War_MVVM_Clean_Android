package com.mvvm_clean.star_wars.features.login

import android.content.Context
import android.content.Intent
import com.mvvm_clean.star_wars.core.base.BaseActivity

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()
}
