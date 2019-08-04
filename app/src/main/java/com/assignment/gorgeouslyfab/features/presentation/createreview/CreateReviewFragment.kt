package com.assignment.gorgeouslyfab.features.presentation.createreview

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.extension.visible
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_create_review.*

/**
 * Created by danieh on 04/08/2019.
 */
class CreateReviewFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_create_review

    private val garmentAdapter = GroupAdapter<ViewHolder>()

    private var garmentTypeSelected: GarmentType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFirstTime)
            setupRecyclerView()

        garment_next_button.setOnClickListener {
            if (!isTablet) {

                findNavController().navigate(CreateReviewFragmentDirections.actionCreateReviewFragmentToDesignerFragment())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("garment_selected", garmentTypeSelected)
    }

    private fun setupRecyclerView() {

        recycler_garments.apply {
            val isTablet = resources.getBoolean(R.bool.isTablet)
            layoutManager = when {
                isTablet -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                else -> GridLayoutManager(context, 3)
            }
            adapter = garmentAdapter
        }

        garmentAdapter.addAll(GarmentType.getGarmentTypes().map {
            GarmentItem(it, clickListener = { garmentItemClicked, position ->
                for (i in 0 until garmentAdapter.itemCount) {
                    val garmentItem = garmentAdapter.getItem(i) as GarmentItem
                    if (i != position) garmentItem.isSelected = false
                }
                garmentAdapter.notifyDataSetChanged()

                val selected = (garmentAdapter.getItem(position) as GarmentItem).isSelected
                garmentTypeSelected = if (selected) garmentItemClicked else null
                garment_next_button.isEnabled = garmentTypeSelected != null
            })
        })
        recycler_garments.visible()
    }
}
