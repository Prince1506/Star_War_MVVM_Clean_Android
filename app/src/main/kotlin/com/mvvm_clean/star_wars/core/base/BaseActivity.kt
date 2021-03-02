package com.mvvm_clean.star_wars.core.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mvvm_clean.star_wars.R
import com.mvvm_clean.star_wars.core.domain.extension.inTransaction
import com.mvvm_clean.star_wars.core.domain.extension.isVisible
import com.mvvm_clean.star_wars.features.people_details.presentation.fragments.PeopleDetailsFragment
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.fragments.PeopleListFragment
import kotlinx.android.synthetic.main.base_activity_layout.*
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
        replaceFragment(PeopleListFragment(), Bundle(), true)
    }

    internal fun changeActionBarTitle(title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        if (!pb_fact_list.isVisible()) {
            val peopleDetailsFragment =
                supportFragmentManager.findFragmentByTag(PeopleDetailsFragment::class.java.simpleName)
            val peopleListFragment =
                supportFragmentManager.findFragmentByTag(PeopleListFragment::class.java.simpleName)

            if (peopleDetailsFragment != null && peopleDetailsFragment.isVisible) {
                peopleDetailsFragment.fragmentManager?.popBackStack()
            } else if (peopleListFragment != null && peopleListFragment.isVisible) {
                finishAfterTransition()
            }
        }
    }

    //  Helper Methods-----------------------------------------
    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer,
                fragment()
            )
        }

    open fun replaceFragment(
        fragment: BaseFragment,
        bundle: Bundle,
        addToBackStack: Boolean
    ) {
        try {
            if (!isFinishing && !isDestroyed) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                Log.d("nameclass", fragment.javaClass.simpleName)
                ft.replace(R.id.fragmentContainer, fragment, fragment.javaClass.simpleName)
                if (addToBackStack) {
                    ft.setReorderingAllowed(true)
                    ft.addToBackStack(null)
                }
                ft.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun fragment(): BaseFragment
}
