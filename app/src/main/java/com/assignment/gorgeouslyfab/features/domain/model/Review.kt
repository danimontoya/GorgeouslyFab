package com.assignment.gorgeouslyfab.features.domain.model

import android.net.Uri
import com.assignment.gorgeouslyfab.features.data.ReviewEntity
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView

/**
 * Created by danieh on 04/08/2019.
 */
data class Review(
        private val garment: String,
        private val designer: String,
        private val feel: String,
        private val picture: Uri
) {

    fun toReviewView() = ReviewView(garment, designer, feel, picture)

    fun toReviewEntity() = ReviewEntity(garment, designer, feel, picture)
}