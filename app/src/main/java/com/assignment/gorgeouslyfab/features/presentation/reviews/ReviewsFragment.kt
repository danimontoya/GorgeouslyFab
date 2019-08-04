package com.assignment.gorgeouslyfab.features.presentation.reviews

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_reviews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewsAdapter
        }

        viewModel.getReviews()
    }

    private fun showReviews(reviewList: List<ReviewView>?) {
        progress_reviews.gone()
        if (reviewList != null && reviewList.isNotEmpty()) {
            val items = reviewList.map { reviewView ->
                ReviewItem(reviewView, clickListener = { recipe -> })
            }
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
