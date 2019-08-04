package com.assignment.gorgeouslyfab.features.data.repository

import arrow.core.Either
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.features.data.ReviewEntity
import com.assignment.gorgeouslyfab.features.domain.model.Review
import javax.inject.Inject

/**
 * Created by danieh on 04/08/2019.
 */
interface ReviewsRepository {

    fun reviews(): Either<Failure, List<Review>>

    fun addReview(review: Review): Either<Failure, Boolean>

    class MemoryDisk @Inject constructor() : ReviewsRepository {

        private var reviews = mutableListOf(
            ReviewEntity(
                "T-Shirt",
                "Lacoste",
                "Nice",
                "https://picture.bestsecret.com/static/images/990/image_31419288_36_620x757_3.jpg"
            ),
            ReviewEntity(
                "Jumper",
                "Marc O'Polo",
                "Amazing",
                "https://picture.bestsecret.com/static/images/1044/image_31430833_40_620x757_3.jpg"
            ),
            ReviewEntity(
                "Shoes",
                "Timberland",
                "Comfortable",
                "https://picture.bestsecret.com/static/images/1037/image_31456750_75_620x757_0.jpg"
            )
        )

        override fun reviews(): Either<Failure, List<Review>> = Either.Right(reviews.map { it.toReview() })

        override fun addReview(review: Review): Either<Failure, Boolean> {
            reviews.add(review.toReviewEntity())
            return Either.Right(true)
        }
    }
}
