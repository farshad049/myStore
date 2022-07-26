package com.example.mystore.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.model.domain.DomainProduct
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    val store: Store<ApplicationState>
    ):ViewModel() {


    fun refreshProducts(){
        viewModelScope.launch {
            val response=repository.getProduct()
            store.update {
                return@update it.copy(products = response)
            }

            delay(5000)
            store.update {
                return@update it.copy(favoriteProductIds = setOf(1,2,4) )
            }

        }
    }




}