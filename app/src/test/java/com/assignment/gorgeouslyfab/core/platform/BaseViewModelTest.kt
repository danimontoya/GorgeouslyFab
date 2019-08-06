package com.assignment.gorgeouslyfab.core.platform

import androidx.lifecycle.MutableLiveData
import com.assignment.gorgeouslyfab.AndroidTest
import com.assignment.gorgeouslyfab.core.exception.Failure
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.Test

/**
 * Created by danieh on 06/08/2019.
 */
class BaseViewModelTest : AndroidTest() {

    @Test
    fun `should handle failure by updating live data`() {
        val viewModel = MyViewModel()

        viewModel.handleError(Failure.ErrorCreatingReview())

        val failure = viewModel.failure
        val error = viewModel.failure.value

        assertThat(failure, instanceOf(MutableLiveData::class.java))
        assertThat(error, instanceOf(Failure.ErrorCreatingReview::class.java))
    }

    private class MyViewModel : BaseViewModel() {
        fun handleError(failure: Failure) = handleFailure(failure)
    }
}