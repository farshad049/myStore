package com.example.mystore.util

import com.example.mystore.data.model.domain.DomainProduct
import com.example.mystore.data.model.domain.Filter
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    fun generateFrom(productList:List<DomainProduct>):Set<Filter>{
        return productList.groupBy { it.category }.map {mapEntry->
            Filter(filterOriginalName = mapEntry.key , filterDisplayName = "${mapEntry.key} (${mapEntry.value.size})")
        }.toSet()
    }
}