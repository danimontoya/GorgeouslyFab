package com.assignment.gorgeouslyfab.features.presentation.createreview

import android.net.Uri

/**
 * Created by danieh on 05/08/2019.
 */
interface CreateReviewListener {
    fun getData(): String
    fun getUri(): Uri? {
        return null
    }
}