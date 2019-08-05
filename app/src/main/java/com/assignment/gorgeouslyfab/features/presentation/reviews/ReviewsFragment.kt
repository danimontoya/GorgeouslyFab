package com.assignment.gorgeouslyfab.features.presentation.reviews

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.core.extension.*
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.layout_row_review.*
import timber.log.Timber

/**
 * Created by danieh on 04/08/2019.
 */
class ReviewsFragment : BaseFragment() {

    companion object {
        private val TAG = ReviewsFragment::class.java.simpleName
    }

    override fun layoutId() = R.layout.fragment_reviews

    private lateinit var viewModel: ReviewsViewModel

    private val reviewsAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            observe(reviewList, ::showReviews)
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.tag(TAG).d("backStackEntryCount: ${fragmentManager?.backStackEntryCount}")
        Timber.tag(TAG).d("fragments: ${fragmentManager?.fragments}")

        if (isFirstTime) {
            recycler_reviews.apply {
                layoutManager = GridLayoutManager(context, spanCount(resources.getBoolean(R.bool.isTablet)))
                adapter = reviewsAdapter
            }

            fab.setOnClickListener {
                findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToCreateReviewFragment())
            }
        }
        viewModel.getReviews()
    }

    private fun spanCount(isTablet: Boolean) = if (isTablet) 4 else 2

    private fun showReviews(reviewList: List<ReviewView>?) {
        progress_reviews.gone()
        empty_reviews.gone()
        if (reviewList != null && reviewList.isNotEmpty()) {
            val items = reviewList.map { reviewView -> ReviewItem(reviewView, clickListener = { }) }
            reviewsAdapter.clear()
            reviewsAdapter.addAll(items)
            recycler_reviews.visible()
        } else {
            empty_reviews.visible()
            context?.let {
                Glide.with(it)
                        .load(R.mipmap.ic_selfie_time)
                        .error(Glide.with(it).load(R.mipmap.ic_selfie_time_viewholder))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(review_image)
            }
            review_garment.text = getString(R.string.empty_review_garment)
            review_feel.text = getString(R.string.empty_review_feels)
            review_designer.text = getString(R.string.empty_review_designer)
            notify(getString(R.string.reviews_no_results))
        }
    }

    private fun showError(failure: Failure?) {
        progress_reviews.gone()
        when (failure) {
            is Failure.BaseFailure -> {
                Timber.tag(TAG).d("Error")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                fragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
