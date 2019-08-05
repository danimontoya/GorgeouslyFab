package com.assignment.gorgeouslyfab.features.presentation.model

import android.net.Uri
import android.os.Parcelable
import com.assignment.gorgeouslyfab.features.domain.model.Review
import kotlinx.android.parcel.Parcelize

/**
 * Created by danieh on 04/08/2019.
 */
@Parcelize
data class ReviewView(
    var garment: String = "",
    var designer: String= "",
    var feel: String = "",
    var picture: Uri = Uri.EMPTY
) : Parcelable {

    fun toReview() = Review(garment, designer, feel, picture)
}
