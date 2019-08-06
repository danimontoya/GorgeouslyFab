package com.assignment.gorgeouslyfab.features.presentation.reviews

import android.net.Uri
import arrow.core.Either
import com.assignment.gorgeouslyfab.AndroidTest
import com.assignment.gorgeouslyfab.features.domain.model.Review
import com.assignment.gorgeouslyfab.features.domain.usecase.GetReviewsUseCase
import com.nhaarman.mockito_kotlin.any
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.notification.Failure
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by danieh on 06/08/2019.
 */
class ReviewsViewModelTest : AndroidTest() {

    @Mock
    lateinit var getReviewsUseCase: GetReviewsUseCase

    private lateinit var reviewsViewModel: ReviewsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        reviewsViewModel = ReviewsViewModel(getReviewsUseCase)
    }

    @Test
    fun `get reviews should all reviews created`() {

        // Given or Arrange
        val reviews = listOf(
            Review("garment1", "designer1", "feel1", Uri.parse("myapp://reviews/images")),
            Review("garment2", "designer2", "feel2", Uri.parse("myapp://reviews/images"))
        )
        val reviewsResult = reviews.map { it.toReviewView() }

        Mockito.`when`(getReviewsUseCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<Review>>) -> Unit>(1)(Either.Right(reviews))
        }

        // Then or Assert
        reviewsViewModel.reviewList.observeForever {
            it shouldEqual reviewsResult
        }

        // When or Act
        runBlocking { reviewsViewModel.getReviews() }
    }

}