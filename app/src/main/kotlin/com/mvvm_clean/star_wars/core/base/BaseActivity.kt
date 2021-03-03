package com.mvvm_clean.star_wars.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.domain.extension.inTransaction
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    //  Override Methods--------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_layout)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    //  Helper Methods-----------------------------------------
    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer,
                fragment()
            )
        }

    abstract fun fragment(): BaseFragment
}
