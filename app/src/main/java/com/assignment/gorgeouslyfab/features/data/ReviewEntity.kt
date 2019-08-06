package com.assignment.gorgeouslyfab.features.data

import android.net.Uri
import com.assignment.gorgeouslyfab.features.domain.model.Review

/**
 * Created by danieh on 04/08/2019.
 */
data class ReviewEntity(
        val garment: String,
        val designer: String,
        val feel: String,
        val picture: Uri
) {

    fun toReview() = Review(garment, designer, feel, picture)
}