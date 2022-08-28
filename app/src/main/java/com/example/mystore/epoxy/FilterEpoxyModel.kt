package com.example.mystore.epoxy


import androidx.core.content.ContextCompat
import com.example.mystore.R
import com.example.mystore.databinding.ModelFilterBinding
import com.example.mystore.model.domain.Filter
import com.example.mystore.model.ui.UiFilter

data class FilterEpoxyModel(
    val uiFilter : UiFilter,
    val onFilterClick : (Filter) -> Unit
):ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {
        tvFilterName.text=uiFilter.filter.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filter) }

        val cardBackgroundColor = if(uiFilter.isSelected) R.color.purple_500 else R.color.purple_200

        root.setCardBackgroundColor(ContextCompat.getColor(root.context , cardBackgroundColor))

    }

}
