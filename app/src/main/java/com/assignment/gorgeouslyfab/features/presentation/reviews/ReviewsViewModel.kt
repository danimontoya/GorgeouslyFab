package com.assignment.gorgeouslyfab.features.presentation.reviews

import androidx.lifecycle.MutableLiveData
import com.assignment.gorgeouslyfab.core.interactor.UseCase
import com.assignment.gorgeouslyfab.core.platform.BaseViewModel
import com.assignment.gorgeouslyfab.features.domain.model.Review
import com.assignment.gorgeouslyfab.features.domain.usecase.AddReviewUseCase
import com.assignment.gorgeouslyfab.features.domain.usecase.GetReviewsUseCase
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
class ReviewsViewModel @Inject constructor(
        private val getReviewsUseCase: GetReviewsUseCase,
        private val addReviewUseCase: AddReviewUseCase
) : BaseViewModel() {

    var reviewList: MutableLiveData<List<ReviewView>> = MutableLiveData()
    var reviewCreated: MutableLiveData<Boolean> = MutableLiveData()

    fun getReviews() = getReviewsUseCase(UseCase.None()) {
        it.fold(::handleFailure, ::handleReviews)
    }

    fun createReview(reviewView: ReviewView) = addReviewUseCase(AddReviewUseCase.Params(reviewView)) {
        it.fold(::handleFailure, ::handleReviewCreated)
    }

    private fun handleReviews(reviews: List<Review>) {
        reviewList.value = reviews.map { it.toReviewView() }
    }

    private fun handleReviewCreated(isCreated: Boolean) {
        reviewCreated.value = isCreated
    }
}