package com.assignment.gorgeouslyfab.features.presentation.selfie

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.assignment.gorgeouslyfab.features.presentation.reviews.ReviewsFragment
import kotlinx.android.synthetic.main.fragment_designer.*
import kotlinx.android.synthetic.main.fragment_selfie.*
import timber.log.Timber

/**
 * Created by danieh on 04/08/2019.
 */
class SelfieFragment : BaseFragment() {

    companion object {
        private val TAG = SelfieFragment::class.java.simpleName
    }

    override fun layoutId() = R.layout.fragment_selfie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.tag(TAG).d("backStackEntryCount: ${fragmentManager?.backStackEntryCount}")
        Timber.tag(TAG).d("fragments: ${fragmentManager?.fragments}")

        text.setOnClickListener {
            //val isTablet = resources.getBoolean(R.bool.isTablet)
            findNavController().navigate(SelfieFragmentDirections.actionSelfieFragmentToReviewsFragment())
        }
    }
}