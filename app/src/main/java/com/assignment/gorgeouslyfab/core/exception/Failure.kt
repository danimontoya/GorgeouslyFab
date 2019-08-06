package com.assignment.gorgeouslyfab.core.exception

/**
 * Created by danieh on 04/08/2019.
 */
sealed class Failure {

    abstract class BaseFailure : Failure()

    class ErrorCreatingReview : BaseFailure()
}