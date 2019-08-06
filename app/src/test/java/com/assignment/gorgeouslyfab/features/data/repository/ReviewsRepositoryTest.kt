package com.assignment.gorgeouslyfab.features.data.repository

import android.net.Uri
import arrow.core.Either
import com.assignment.gorgeouslyfab.UnitTest
import com.assignment.gorgeouslyfab.features.data.ReviewEntity
import com.assignment.gorgeouslyfab.features.domain.model.Review
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by danieh on 06/08/2019.
 */
class ReviewsRepositoryTest : UnitTest() {

    private lateinit var repository: ReviewsRepository.MemoryDisk

    @Before
    fun setUp() {
        repository = ReviewsRepository.MemoryDisk()
    }

    @Test
    fun `reviews method should return list of reviews`() {
        // Given-Arrange
        val reviewEntityList = mutableListOf(
                ReviewEntity("garment1", "designer1", "feel1", Uri.parse("myapp://reviews/images")),
                ReviewEntity("garment2", "designer2", "feel2", Uri.parse("myapp://reviews/images"))
        )
        val resultReviews = reviewEntityList.map { it.toReview() }
        repository.setReviews(reviewEntityList)

        // When-Act
        val result = repository.reviews()

        // Then-Assert
        result.isRight() shouldEqual true
        result shouldEqual Either.Right(resultReviews)
    }

    @Test
    fun `add review method should return list of reviews`() {
        // Given-Arrange
        val review = Review("garment1", "designer1", "feel1", Uri.parse("myapp://reviews/images"))
        repository.setReviews(mutableListOf())

        // When-Act
        val result = repository.addReview(review)

        // Then-Assert
        result.isRight() shouldEqual true
        result shouldEqual Either.Right(true)
    }
}
