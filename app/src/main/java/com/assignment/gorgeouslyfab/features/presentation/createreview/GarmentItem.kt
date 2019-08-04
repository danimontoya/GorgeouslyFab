package com.assignment.gorgeouslyfab.features.presentation.createreview

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.assignment.gorgeouslyfab.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.layout_row_garment.view.*

/**
 * Created by danieh on 04/08/2019.
 */
class GarmentItem(
        private val garmentType: GarmentType,
        var isSelected: Boolean = false,
        private val clickListener: (GarmentType, Int) -> Unit = { _, _ -> }
) : Item() {

    override fun getLayout(): Int = R.layout.layout_row_garment

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {

            Glide.with(itemView.context)
                    .load(garmentType.resource)
                    .error(Glide.with(itemView.context).load(R.mipmap.ic_review_viewholder))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.review_garment_image)
            itemView.review_garment_type.text = garmentType.type

            if (isSelected) {
                itemView.review_garment_container.setBackgroundColor(ContextCompat.getColor(itemView.review_garment_container.context, R.color.colorAccent))
            } else {
                itemView.review_garment_container.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                isSelected = !isSelected
                clickListener(garmentType, adapterPosition)
            }
        }
    }
}
