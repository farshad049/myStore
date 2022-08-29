package com.example.mystore.ui

import android.os.Bundle
import android.view.View
import com.example.mystore.R
import com.example.mystore.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment:BaseFragment(R.layout.fragment_cart) {

    private var _binding : FragmentCartBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentCartBinding.bind(view)








    }//FUN










    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}