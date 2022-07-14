package com.example.mystore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.mystore.databinding.ActivityMainBinding
import com.example.mystore.hilt.service.ProductService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var productService: ProductService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refreshData()
        setOnClickListener()







    }//FUN

    private fun refreshData(){
        lifecycleScope.launchWhenStarted {
            binding.progressImage.isVisible=true
            val response = productService.getAllProducts()
            binding.ivProduct.load("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"){
                listener { request, result ->
                    binding.progressImage.isGone=true
                }
            }
            Log.i("DATA",response!!.body().toString())
        }
    }

    private fun setOnClickListener(){
        binding.cardProduct.setOnClickListener {
            binding.tvDescription.apply {
                isVisible=!isVisible
            }
        }

        binding.btnAddToCard.setOnClickListener {
            binding.btnSuccessAdded.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite=false
        binding.btnFavorite.setOnClickListener {
            var icon=if (isFavorite) R.drawable.ic_round_favorite_border_24 else R.drawable.ic_round_favorite_24
            binding.btnFavorite.setIconResource(icon)
            isFavorite=!isFavorite
        }
    }


}