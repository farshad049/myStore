package com.example.mystore.data.model.ui

import com.example.mystore.data.model.domain.DomainProduct

data class UiProduct (
    val product : DomainProduct,
    val isFavorite :Boolean = false,
    val isExpanded : Boolean = false,
    val isInCart : Boolean = false
        )