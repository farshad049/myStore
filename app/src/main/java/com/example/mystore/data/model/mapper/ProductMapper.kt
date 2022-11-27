package com.example.mystore.data.model.mapper

import com.example.mystore.data.model.domain.DomainProduct
import com.example.mystore.data.model.network.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun buildFrom(networkProduct: NetworkProduct): DomainProduct {
        return DomainProduct(
            category = capitalize(networkProduct.category),
            description = networkProduct.description,
            id = networkProduct.id,
            image = networkProduct.image,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP),
            title = networkProduct.title ,
            rating = DomainProduct.Rating(rate = networkProduct.rating.rate , ratingCount = networkProduct.rating.count)
        )
    }
}

private fun capitalize(string: String):String{
    return string.replaceFirstChar {
        if (it.isLowerCase()){
            it.titlecase(Locale.getDefault())
        }else{
            it.toString()
        }
    }
}