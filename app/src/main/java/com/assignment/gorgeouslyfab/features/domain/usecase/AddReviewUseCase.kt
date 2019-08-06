package com.assignment.gorgeouslyfab.features.domain.usecase

import com.assignment.gorgeouslyfab.core.interactor.UseCase
import com.assignment.gorgeouslyfab.features.data.repository.ReviewsRepository
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
class AddReviewUseCase @Inject constructor(private val reviewsRepository: ReviewsRepository) :
        UseCase<Boolean, AddReviewUseCase.Params>() {

    override suspend fun run(params: Params) = reviewsRepository.addReview(params.reviewView.toReview())

    data class Params(val reviewView: ReviewView)
}