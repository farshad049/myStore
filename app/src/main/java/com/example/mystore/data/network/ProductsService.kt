package com.example.mystore.data.network

import com.example.mystore.data.model.network.NetworkProduct
import retrofit2.Response
import retrofit2.http.GET


interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}