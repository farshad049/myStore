package com.example.mystore.ui.productList.epoxy


import androidx.core.content.ContextCompat
import com.example.mystore.R
import com.example.mystore.databinding.ModelFilterBinding
import com.example.mystore.data.model.ui.UiFilter
import com.example.mystore.epoxy.ViewBindingKotlinModel

data class FilterEpoxyModel(
    val uiFilter : UiFilter,
    val onFilterClick : (UiFilter.FilterOriginalAndDisplay) -> Unit
): ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {
        tvFilterName.text= uiFilter.filterOriginalAndDisplay.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filterOriginalAndDisplay) }

        val cardBackgroundColor = if(uiFilter.isSelected) R.color.purple_500 else R.color.purple_200

        root.setCardBackgroundColor(ContextCompat.getColor(root.context , cardBackgroundColor))

    }

}
