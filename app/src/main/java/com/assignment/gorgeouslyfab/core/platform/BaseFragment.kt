package com.assignment.gorgeouslyfab.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.assignment.gorgeouslyfab.GorgeouslyFabApp
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.di.ApplicationComponent
import com.assignment.gorgeouslyfab.core.extension.viewContainer
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    var isTablet = false
    var isFirstTime = true

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as GorgeouslyFabApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var fragmentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(layoutId(), container, false)
        } else {
            isFirstTime = false
        }
        return fragmentView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isTablet = resources.getBoolean(R.bool.isTablet)
        setHasOptionsMenu(true)
    }

    internal fun notify(message: String) = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG).show()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
