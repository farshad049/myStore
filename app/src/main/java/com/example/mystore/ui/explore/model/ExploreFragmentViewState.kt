package com.example.mystore.ui.explore.model

import com.example.mystore.data.model.ui.UiProduct

sealed class ExploreFragmentViewState{
    object Loading: ExploreFragmentViewState()
    data class Error(val errorMessage: String): ExploreFragmentViewState()
    data class Success(
        val selectedUiProduct: UiProduct,
        val quantity: Int,
        val allUiProducts: List<UiProduct>
    ): ExploreFragmentViewState()
}
