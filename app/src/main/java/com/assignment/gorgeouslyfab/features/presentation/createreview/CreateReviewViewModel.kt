package com.assignment.gorgeouslyfab.features.presentation.createreview

import androidx.lifecycle.MutableLiveData
import com.assignment.gorgeouslyfab.core.platform.BaseViewModel
import com.assignment.gorgeouslyfab.features.domain.usecase.AddReviewUseCase
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import javax.inject.Inject

/**
 * Created by danieh on 05/08/2019.
 */
class CreateReviewViewModel @Inject constructor(private val addReviewUseCase: AddReviewUseCase) : BaseViewModel() {

    var reviewCreated: MutableLiveData<Boolean> = MutableLiveData()

    fun createReview(reviewView: ReviewView) = addReviewUseCase(AddReviewUseCase.Params(reviewView)) {
        it.fold(::handleFailure, ::handleReviewCreated)
    }

    private fun handleReviewCreated(isCreated: Boolean) {
        reviewCreated.value = isCreated
    }
}