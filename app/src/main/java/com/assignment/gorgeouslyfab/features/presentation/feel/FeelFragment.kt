package com.assignment.gorgeouslyfab.features.presentation.feel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.extension.gone
import com.assignment.gorgeouslyfab.core.extension.visible
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.assignment.gorgeouslyfab.features.presentation.createreview.CreateReviewListener
import com.assignment.gorgeouslyfab.features.presentation.feel.FeelFragmentArgs.fromBundle
import kotlinx.android.synthetic.main.fragment_feel.*

/**
 * Created by danieh on 04/08/2019.
 */
class FeelFragment : BaseFragment(), TextWatcher, CreateReviewListener {

    override fun getData() = feel

    override fun layoutId() = R.layout.fragment_feel

    private val review by lazy {
        arguments?.let { fromBundle(it).review }
    }

    private var feel: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            isTablet -> {
                feel_next_button.gone()
                feel_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                }
            }
            else -> {
                feel_next_button.visible()
                feel_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                }
            }
        }

        feel_edit.addTextChangedListener(this)
        feel_next_button.setOnClickListener {
            if (feel.isEmpty()) {
                feel_edit.error = getString(R.string.feel_empty)
            } else {
                review?.feel = feel
                val navDirections = FeelFragmentDirections.actionFeelFragmentToSelfieFragment()
                navDirections.setReview(review)

                findNavController().navigate(navDirections)
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        feel_edit.error = null
        feel = s.toString()
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}