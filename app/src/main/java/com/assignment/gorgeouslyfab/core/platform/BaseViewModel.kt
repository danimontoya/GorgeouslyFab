package com.assignment.gorgeouslyfab.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.gorgeouslyfab.core.exception.Failure

/**
 * Created by danieh on 04/08/2019.
 *
 * Base ViewModel class with default Failure handling.
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure?) {
        this.failure.value = failure
    }
}