package com.assignment.gorgeouslyfab.features.presentation.createreview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.core.extension.failure
import com.assignment.gorgeouslyfab.core.extension.observe
import com.assignment.gorgeouslyfab.core.extension.viewModel
import com.assignment.gorgeouslyfab.core.extension.visible
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.assignment.gorgeouslyfab.features.presentation.designer.DesignerFragment
import com.assignment.gorgeouslyfab.features.presentation.feel.FeelFragment
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import com.assignment.gorgeouslyfab.features.presentation.selfie.SelfieFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_create_review.*
import timber.log.Timber

/**
 * Created by danieh on 04/08/2019.
 */
class CreateReviewFragment : BaseFragment() {

    companion object {
        private val TAG = CreateReviewFragment::class.java.simpleName

        private const val GARMENT_SELECTED = "garment_selected"
    }

    override fun layoutId() = R.layout.fragment_create_review

    private lateinit var viewModel: CreateReviewViewModel

    private val garmentAdapter = GroupAdapter<ViewHolder>()

    private var childFragments: MutableList<Fragment>? = null

    private var garmentSelected: GarmentType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        savedInstanceState?.let {
            garmentSelected = savedInstanceState.getParcelable(GARMENT_SELECTED)
        }

        viewModel = viewModel(viewModelFactory) {
            observe(reviewCreated, ::showReviewCreated)
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            isTablet -> {
                childFragments = childFragmentManager.fragments
            }
        }

        setupRecyclerView()

        garment_next_button.setOnClickListener {
            if (!isTablet) {
                val reviewView = ReviewView(garment = garmentSelected?.type.toString())
                val navDirections = CreateReviewFragmentDirections.actionCreateReviewFragmentToDesignerFragment()
                navDirections.setReview(reviewView)

                findNavController().navigate(navDirections)

            } else {
                val garment = garmentSelected?.type.toString()
                val designer = getDesigner()
                val feel = getFeel()
                val selfie = getSelfie()

                if (garment.isNotEmpty() && designer.isNotEmpty() && feel.isNotEmpty() && selfie != null) {
                    viewModel.createReview(ReviewView(garment, designer, feel, selfie))
                } else {
                    notify("Please complete your review before creating it!")
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GARMENT_SELECTED, garmentSelected)
        Timber.tag(TAG).d("onSaveInstanceState: $outState")
    }

    private fun setupRecyclerView() {

        recycler_garments.apply {
            val isTablet = resources.getBoolean(R.bool.isTablet)
            layoutManager = when {
                isTablet -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                else -> GridLayoutManager(context, 3)
            }
            adapter = garmentAdapter
        }

        garmentAdapter.clear()
        garmentAdapter.addAll(GarmentType.getGarmentTypes().map {
            var isSelected = false
            if (garmentSelected != null && garmentSelected?.type == it.type) {
                isSelected = true
            }
            GarmentItem(it, isSelected, clickListener = { garmentItemClicked, position ->
                for (i in 0 until garmentAdapter.itemCount) {
                    val garmentItem = garmentAdapter.getItem(i) as GarmentItem
                    if (i != position) garmentItem.isSelected = false
                }
                garmentAdapter.notifyDataSetChanged()

                val selected = (garmentAdapter.getItem(position) as GarmentItem).isSelected
                garmentSelected = if (selected) garmentItemClicked else null
                garment_next_button.isEnabled = garmentSelected != null
            })
        })
        recycler_garments.visible()
    }

    private fun getDesigner() = (childFragments?.first { it is DesignerFragment } as DesignerFragment).getData()

    private fun getFeel() = (childFragments?.first { it is FeelFragment } as FeelFragment).getData()

    private fun getSelfie() = (childFragments?.first { it is SelfieFragment } as SelfieFragment).getUri()

    private fun showReviewCreated(isCreated: Boolean?) {
        isCreated?.let {
            findNavController().popBackStack()
        } ?: Timber.tag(TAG).d("showReviewCreatedError")
    }

    private fun showError(failure: Failure?) {
        //progress_reviews.gone()
        when (failure) {
            is Failure.BaseFailure -> {
                Timber.tag(TAG).d("showError")
            }
        }
    }
}
