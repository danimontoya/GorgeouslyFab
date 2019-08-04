package com.assignment.gorgeouslyfab.core.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.assignment.gorgeouslyfab.features.presentation.MainActivity
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by danieh on 04/08/2019.
 */
inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

val BaseFragment.viewContainer: View get() = (activity as MainActivity).root
