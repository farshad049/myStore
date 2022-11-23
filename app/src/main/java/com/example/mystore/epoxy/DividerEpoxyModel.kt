package com.example.mystore.epoxy

import androidx.annotation.Dimension
import androidx.annotation.Dimension.PX
import androidx.core.view.updatePadding
import com.example.mystore.R
import com.example.mystore.databinding.ModelDividerBinding

data class DividerEpoxyModel(
    @Dimension(unit = PX) private val horizontalPadding: Int = 0,
    @Dimension(unit = PX) private val verticalPadding: Int = 0
) : ViewBindingKotlinModel<ModelDividerBinding>(R.layout.model_divider) {

    override fun ModelDividerBinding.bind() {
        root.updatePadding(
            left = horizontalPadding, right = horizontalPadding,
            top = verticalPadding, bottom = verticalPadding
        )
    }
}