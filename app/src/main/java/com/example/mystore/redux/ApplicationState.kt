package com.example.mystore.redux

import com.example.mystore.data.model.domain.DomainProduct
import com.example.mystore.data.model.domain.Filter
import com.example.mystore.data.model.domain.DomainUser


data class ApplicationState(
    val user : UserLoginResponse = UserLoginResponse.UnAuthenticated(),
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


    sealed interface UserLoginResponse{
        data class Authenticated (val user : DomainUser) : UserLoginResponse
        data class UnAuthenticated (val error : String? = null) : UserLoginResponse

        fun getGreetingMessage():String{
            return if (this is Authenticated) user.greetingMessage else "Sign in"
        }

        fun getEmail():String{
            return if (this is Authenticated) user.email else ""
        }
    }


}
