package com.assignment.gorgeouslyfab.features.presentation.createreview

import android.os.Parcelable
import com.assignment.gorgeouslyfab.R
import kotlinx.android.parcel.Parcelize

/**
 * Created by danieh on 04/08/2019.
 */
@Parcelize
data class GarmentType(val type: String, val resource: Int) : Parcelable {

    companion object {
        private val types = listOf(
            GarmentType("Trousers", R.mipmap.ic_garment_trousers),
            GarmentType("Jeans", R.mipmap.ic_garment_jeans),
            GarmentType("Shirts", R.mipmap.ic_garment_shirts),
            GarmentType("T-Shirts", R.mipmap.ic_garment_tshirt),
            GarmentType("Jumpers", R.mipmap.ic_garment_jumper),
            GarmentType("Coats", R.mipmap.ic_garment_coat),
            GarmentType("Shoes", R.mipmap.ic_garment_shoes)
        )

        fun getGarmentTypes() = types
    }
}