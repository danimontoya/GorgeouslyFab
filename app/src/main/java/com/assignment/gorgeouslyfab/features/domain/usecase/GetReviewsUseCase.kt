package com.assignment.gorgeouslyfab.features.domain.usecase

import com.assignment.gorgeouslyfab.core.interactor.UseCase
import com.assignment.gorgeouslyfab.features.data.repository.ReviewsRepository
import com.assignment.gorgeouslyfab.features.domain.model.Review
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
class GetReviewsUseCase @Inject constructor(private val reviewsRepository: ReviewsRepository) :
        UseCase<List<Review>, UseCase.None>() {

    override suspend fun run(params: None) = reviewsRepository.reviews()
}