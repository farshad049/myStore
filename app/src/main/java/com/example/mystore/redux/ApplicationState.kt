package com.example.mystore.redux

import com.example.mystore.model.domain.DomainProduct

data class ApplicationState(
    val products:List<DomainProduct> = emptyList(),
    val favoriteProductIds :Set<Int> = emptySet()
)
