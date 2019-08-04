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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_reviews.*
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
        setHasOptionsMenu(true)

        viewModel = viewModel(viewModelFactory) {
            observe(reviewList, ::showReviews)
            observe(reviewCreated, ::showReviewCreated)
            failure(failure, ::showError)
        }
    }

    private fun showReviewCreated(isCreated: Boolean?) {
        isCreated?.let {
            viewModel.getReviews()
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


            val review1 = ReviewView(
                    "T-Shirt",
                    "Lacoste",
                    "Nice",
                    "https://picture.bestsecret.com/static/images/990/image_31419288_36_620x757_3.jpg"
            )
            viewModel.createReview(review1)
            val review2 = ReviewView(
                    "Jumper",
                    "Marc O'Polo",
                    "Amazing",
                    "https://picture.bestsecret.com/static/images/1044/image_31430833_40_620x757_3.jpg"
            )
            viewModel.createReview(review2)
            val review3 = ReviewView(
                    "Shoes",
                    "Timberland",
                    "Comfortable",
                    "https://picture.bestsecret.com/static/images/1037/image_31456750_75_620x757_0.jpg"
            )
            viewModel.createReview(review3)
        }
        //viewModel.getReviews()
    }

    private fun spanCount(isTablet: Boolean): Int {
        return if (isTablet) 3 else 2
    }

    private fun showReviews(reviewList: List<ReviewView>?) {
        progress_reviews.gone()
        if (reviewList != null && reviewList.isNotEmpty()) {
            val items = reviewList.map { reviewView -> ReviewItem(reviewView, clickListener = { }) }
            reviewsAdapter.clear()
            reviewsAdapter.addAll(items)
            recycler_reviews.visible()
        } else {
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
