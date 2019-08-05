package com.assignment.gorgeouslyfab.features.presentation.designer

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
import com.assignment.gorgeouslyfab.features.presentation.designer.DesignerFragmentArgs.fromBundle
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import kotlinx.android.synthetic.main.fragment_designer.*

/**
 * Created by danieh on 04/08/2019.
 */
class DesignerFragment : BaseFragment(), TextWatcher, CreateReviewListener {

    override fun getData() = designer

    override fun layoutId() = R.layout.fragment_designer

    private val review by lazy {
        arguments?.let { fromBundle(it).review }
    }

    private var designer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFirstTime) {
            if (!isTablet) {
                designer_next_button.visible()
                designer_root.apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
            } else {
                designer_next_button.gone()
                designer_root.apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }

        designer_edit.addTextChangedListener(this)
        designer_next_button.setOnClickListener {
            if (designer.isEmpty()) {
                designer_edit.error = getString(R.string.designer_empty)
            } else {
                review?.designer = designer
                val navDirections = DesignerFragmentDirections.actionDesignerFragmentToFeelFragment()
                navDirections.setReview(review)

                findNavController().navigate(navDirections)
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        designer_edit.error = null
        designer = s.toString()
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}