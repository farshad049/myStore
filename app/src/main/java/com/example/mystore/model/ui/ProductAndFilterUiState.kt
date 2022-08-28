package com.example.mystore.model.ui

data class ProductAndFilterUiState (
        val filters : Set<UiFilter> ,
        val products : List<UiProduct>
        )