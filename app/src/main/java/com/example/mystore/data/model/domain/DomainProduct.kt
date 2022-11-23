package com.example.mystore.data.model.domain

import java.math.BigDecimal

data class DomainProduct(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: BigDecimal,
    val title: String
)
