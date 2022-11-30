package com.example.mystore.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.Dimension.PX
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.example.mystore.R
import com.example.mystore.databinding.ModelDividerBinding

data class DividerEpoxyModel(
    @Dimension(unit = PX) private val horizontalMargin: Int = 0,
    @Dimension(unit = PX) private val verticalMargin: Int = 0
) : ViewBindingKotlinModel<ModelDividerBinding>(R.layout.model_divider) {

    override fun ModelDividerBinding.bind() {
        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(
                horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin
            )
        }
    }
}