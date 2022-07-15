package com.example.mystore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mystore.databinding.ActivityMainBinding
import com.example.mystore.network.ProductsService
import com.example.mystore.model.domain.Product
import com.example.mystore.model.mapper.ProductMapper
import com.example.mystore.model.network.NetworkProduct
import com.example.mystore.ui.ProductEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var productsService: ProductsService
    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)
        //set controller.data to empty list in order to handle loading state in epoxy controller
        controller.setData(emptyList())

        lifecycleScope.launchWhenStarted {
            val response:Response<List<NetworkProduct>> = productsService.getAllProducts()
            val domainProduct: List<Product> = response.body()?.map {
                productMapper.buildFrom(networkProduct = it)
            }?: emptyList()

            controller.setData(domainProduct)
            Log.i("DATA",response!!.body().toString())
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