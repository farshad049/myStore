package com.example.mystore.util

import com.example.mystore.data.model.domain.DomainProduct
import com.example.mystore.data.model.ui.UiFilter
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    fun generateFrom(productList:List<DomainProduct>):Set<UiFilter.FilterOriginalAndDisplay>{
        return productList.groupBy { it.category }.map {mapEntry->
            UiFilter.FilterOriginalAndDisplay(filterOriginalName = mapEntry.key , filterDisplayName = "${mapEntry.key} (${mapEntry.value.size})")
        }.toSet()
    }
}