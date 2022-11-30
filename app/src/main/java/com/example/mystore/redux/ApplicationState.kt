package com.example.mystore.redux

import com.example.mystore.data.model.domain.DomainProduct
import com.example.mystore.data.model.domain.Filter
import com.example.mystore.data.model.domain.DomainUser


data class ApplicationState(
    val domainUser : DomainUser? = null,
    val products:List<DomainProduct> = emptyList(),
    val favoriteProductIds : Set<Int> = emptySet(),
    val expandedProductIds : Set<Int> = emptySet(),
    val productFilterInfo : ProductFilterInfo= ProductFilterInfo(),
    val inCartProductIds : Set<Int> = emptySet(),
    val cartQuantitiesMap : Map<Int , Int> = emptyMap()  //productId -> quantity
)

{
    data class ProductFilterInfo(
        val filters : Set<Filter> = emptySet(),
        val selectedFilter : Filter? = null
    )
}
