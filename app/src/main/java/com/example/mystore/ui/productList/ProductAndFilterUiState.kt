package com.example.mystore.ui.productList

import com.example.mystore.data.model.ui.UiFilter
import com.example.mystore.data.model.ui.UiProduct

sealed interface ProductAndFilterUiState {

        data class Success(
            val filters : Set<UiFilter>,
            val products : List<UiProduct>
        ): ProductAndFilterUiState

        object Loading: ProductAndFilterUiState
}

