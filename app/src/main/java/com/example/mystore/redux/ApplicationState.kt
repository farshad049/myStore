package com.example.mystore.redux

import com.example.mystore.model.domain.DomainProduct
import com.example.mystore.model.domain.Filter


data class ApplicationState(
    val products:List<DomainProduct> = emptyList(),
    val favoriteProductIds : Set<Int> = emptySet(),
    val expandedProductIds : Set<Int> = emptySet(),
    val productFilterInfo : ProductFilterInfo= ProductFilterInfo(),
    val inCartProductIds : Set<Int> = emptySet()
)

{
    data class ProductFilterInfo(
        val filters : Set<Filter> = emptySet() ,
        val selectedFilter : Filter? = null
    )
}
