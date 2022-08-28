package com.example.mystore.model.ui

sealed interface ProductAndFilterUiState {

        data class Success(
                val filters : Set<UiFilter> ,
                val products : List<UiProduct>
        ):ProductAndFilterUiState

        object Loading: ProductAndFilterUiState
}

