package com.assignment.gorgeouslyfab.features.data.repository

import arrow.core.Either
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.features.data.ReviewEntity
import com.assignment.gorgeouslyfab.features.domain.model.Review
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
interface ReviewsRepository {

    fun reviews(): Either<Failure, List<Review>>

    fun addReview(review: Review): Either<Failure, Boolean>

    class MemoryDisk @Inject constructor() : ReviewsRepository {

        private var reviews = mutableListOf<ReviewEntity>()

        override fun reviews(): Either<Failure, List<Review>> = Either.Right(reviews.map { it.toReview() })

        override fun addReview(review: Review): Either<Failure, Boolean> {
            reviews.add(review.toReviewEntity())
            return Either.Right(true)
        }

        @TestOnly
        fun setReviews(reviewEntityList: MutableList<ReviewEntity>) {
            reviews = reviewEntityList
        }
    }
}
