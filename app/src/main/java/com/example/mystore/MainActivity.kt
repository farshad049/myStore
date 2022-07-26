package com.example.mystore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.mystore.arch.ProductViewModel
import com.example.mystore.databinding.ActivityMainBinding
import com.example.mystore.network.ProductsService
import com.example.mystore.model.domain.DomainProduct
import com.example.mystore.model.mapper.ProductMapper
import com.example.mystore.model.network.NetworkProduct
import com.example.mystore.model.ui.UiProduct
import com.example.mystore.ui.ProductEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var productsService: ProductsService
    @Inject
    lateinit var productMapper: ProductMapper

    private val viewModel:ProductViewModel by lazy {
        ViewModelProvider(this)[ProductViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)
        //set controller.data to empty list in order to handle loading state in epoxy controller
        controller.setData(emptyList())

        viewModel.refreshProducts()

        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds }
        ){listOfProducts,setOfFavoriteProducts ->
            listOfProducts.map {
                UiProduct(it,setOfFavoriteProducts.contains(it.id))//second element is > if in that set of product eny product is favorite
            }
        }.distinctUntilChanged().asLiveData().observe(this){
            controller.setData(it)
        }














    }//FUN



//    private fun setOnClickListener(){
//        binding.cardProduct.setOnClickListener {
//            binding.tvDescription.apply {
//                isVisible=!isVisible
//            }
//        }
//
//        binding.btnAddToCard.setOnClickListener {
//            binding.btnSuccessAdded.apply {
//                isVisible = !isVisible
//            }
//        }
//
//        var isFavorite=false
//        binding.btnFavorite.setOnClickListener {
//            var icon=if (isFavorite) R.drawable.ic_round_favorite_border_24 else R.drawable.ic_round_favorite_24
//            binding.btnFavorite.setIconResource(icon)
//            isFavorite=!isFavorite
//        }
//    }


}