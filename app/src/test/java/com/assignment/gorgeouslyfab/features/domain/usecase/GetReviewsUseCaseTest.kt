package com.assignment.gorgeouslyfab.features.domain.usecase

import android.net.Uri
import arrow.core.Either
import com.assignment.gorgeouslyfab.UnitTest
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.core.interactor.UseCase
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
class GetReviewsUseCaseTest : UnitTest() {

    private lateinit var getReviewsUseCase: GetReviewsUseCase

    @Mock
    private lateinit var reviewsRepository: ReviewsRepository

    @Before
    fun setUp() {
        getReviewsUseCase = GetReviewsUseCase(reviewsRepository)
    }

    @Test
    fun `should get data from repository and return the list of reviews`() {
        val reviews = listOf(
            Review("garment1", "designer1", "feel1", Uri.parse("myapp://reviews/images")),
            Review("garment2", "designer2", "feel2", Uri.parse("myapp://reviews/images"))
        )
        given { reviewsRepository.reviews() }.willReturn(Either.Right(reviews))

        var result: Either<Failure, List<Review>>? = null
        runBlocking {
            result = getReviewsUseCase.run(UseCase.None())
            result
        }

        verify(reviewsRepository).reviews()
        result shouldEqual Either.Right(reviews)
        verifyNoMoreInteractions(reviewsRepository)
    }
}
