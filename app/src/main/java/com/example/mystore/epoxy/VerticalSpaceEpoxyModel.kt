package com.example.mystore.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.PX
import com.example.mystore.R
import com.example.mystore.databinding.ModelVerticalSpaceBinding

data class VerticalSpaceEpoxyModel(
    @Dimension(unit = PX) val height: Int
):ViewBindingKotlinModel<ModelVerticalSpaceBinding>(R.layout.model_vertical_space){
    override fun ModelVerticalSpaceBinding.bind() {
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
    }
}
