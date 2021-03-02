package com.mvvm_clean.star_wars.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mvvm_clean.star_wars.AndroidApplication
import com.mvvm_clean.star_wars.R.color
import com.mvvm_clean.star_wars.core.di.ApplicationComponent
import com.mvvm_clean.star_wars.core.domain.extension.appContext
import com.mvvm_clean.star_wars.core.domain.extension.viewContainer
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.registers.CountingIdlingResourceSingleton
import kotlinx.android.synthetic.main.base_activity_layout.*
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment() {

    //  Abstract Methods----------------------
    abstract fun layoutId(): Int

    // Field Variables ---------------------
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).mAppComponent
    }

    // Annotations Variables -----------------
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //  Override Methods--------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    // Non Final methods-----------------------------------------
    open fun onBackPressed() {}

    //  Helper Methods-----------------------------------------
    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is BaseActivity) this.pb_fact_list.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(
        @StringRes message: Int
    ) {
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE).apply {
            setActionTextColor(ContextCompat.getColor(appContext, color.colorTextPrimary))
            show()
        }
    }

    internal fun handleApiSuccess() = CountingIdlingResourceSingleton.decrement()
    internal fun handleApiFailure() = CountingIdlingResourceSingleton.decrement()
}
