package com.assignment.gorgeouslyfab.features.presentation.reviews

import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.features.presentation.model.ReviewView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.layout_row_review.view.*

/**
 * Created by danieh on 04/08/2019.
 */
class ReviewItem(private val reviewView: ReviewView, private val clickListener: (ReviewView) -> Unit = { _ -> }) : Item() {

    override fun getLayout(): Int = R.layout.layout_row_review

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {

            Glide.with(itemView.context)
                    .load(reviewView.picture)
                    .apply(RequestOptions.centerCropTransform())
                    .error(Glide.with(itemView.context).load(R.mipmap.ic_selfie_time_viewholder))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.review_image)
            itemView.review_garment.text = reviewView.garment
            itemView.review_feel.text = reviewView.feel
            itemView.review_designer.text = reviewView.designer

            itemView.setOnClickListener { clickListener(reviewView) }
        }
    }
}
