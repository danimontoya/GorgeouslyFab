package com.assignment.gorgeouslyfab.features.domain.usecase

import android.net.Uri
import arrow.core.Either
import com.assignment.gorgeouslyfab.UnitTest
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.features.data.repository.ReviewsRepository
import com.assignment.gorgeouslyfab.features.domain.model.Review
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by danieh on 06/08/2019.
 */
class AddReviewUseCaseTest : UnitTest() {

    private lateinit var addReviewUseCase: AddReviewUseCase

    @Mock
    private lateinit var reviewsRepository: ReviewsRepository

    @Before
    fun setUp() {
        addReviewUseCase = AddReviewUseCase(reviewsRepository)
    }

    @Test
    fun `should add review to repository and return true if it was correctly added`() {
        val review = Review("garment1", "designer1", "feel1", Uri.parse("myapp://reviews/images"))
        given { reviewsRepository.addReview(review) }.willReturn(Either.Right(true))

        var result: Either<Failure, Boolean>? = null
        runBlocking {
            result = addReviewUseCase.run(AddReviewUseCase.Params(review.toReviewView()))
            result
        }

        verify(reviewsRepository).addReview(review)
        result shouldEqual Either.Right(true)
        verifyNoMoreInteractions(reviewsRepository)
    }
}
