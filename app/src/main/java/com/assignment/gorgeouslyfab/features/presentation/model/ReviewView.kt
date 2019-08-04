package com.assignment.gorgeouslyfab.features.presentation.model

import android.os.Parcelable
import com.assignment.gorgeouslyfab.features.domain.model.Review
import kotlinx.android.parcel.Parcelize

/**
 * Created by danieh on 04/08/2019.
 */
@Parcelize
data class ReviewView(
    val garment: String,
    val designer: String,
    val feel: String,
    val picture: String
) : Parcelable {

    fun toReview() = Review(garment, designer, feel, picture)
}
