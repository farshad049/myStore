package com.example.mystore.arch

import com.example.mystore.model.domain.DomainProduct
import com.example.mystore.model.mapper.ProductMapper
import com.example.mystore.network.ProductsService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
    ) {

    suspend fun getProduct():List<DomainProduct>{
        return productsService.getAllProducts().let {networkProduct->
            networkProduct.body()?.map { productMapper.buildFrom(it) }
        }?: emptyList()
    }



}