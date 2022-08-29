package com.example.mystore.ui.profile

import android.os.Bundle
import android.view.View
import com.example.mystore.R
import com.example.mystore.databinding.FragmentProductListBinding
import com.example.mystore.databinding.FragmentProfileBinding
import com.example.mystore.ui.BaseFragment


class ProfileFragment:BaseFragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentProfileBinding.bind(view)



    }//FUN



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}