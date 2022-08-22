package com.example.mystore.model.ui

import com.example.mystore.model.domain.DomainProduct

data class UiProduct (
    val product: DomainProduct,
    val isFavorite :Boolean=false,
    val isExpanded:Boolean=false
        )