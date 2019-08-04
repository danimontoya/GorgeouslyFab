package com.assignment.gorgeouslyfab.features.presentation.reviews

import androidx.lifecycle.MutableLiveData
import com.assignment.gorgeouslyfab.core.interactor.UseCase
import com.assignment.gorgeouslyfab.core.platform.BaseViewModel
import com.assignment.gorgeouslyfab.features.domain.model.Review
import com.assignment.gorgeouslyfab.features.domain.usecase.GetReviewsUseCase
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
class ReviewsViewModel @Inject constructor(private val getReviewsUseCase: GetReviewsUseCase) : BaseViewModel() {

    var reviewList: MutableLiveData<List<ReviewView>> = MutableLiveData()

    fun getReviews() = getReviewsUseCase(UseCase.None()) {
        it.fold(::handleFailure, ::handleReviews)
    }

    private fun handleReviews(reviews: List<Review>) {
        reviewList.value = reviews.map { it.toReviewView() }
    }
}