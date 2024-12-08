package com.kocerlabs.simplifiedcodingmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

// Data binding, view model'e ihtiyacımız var.
abstract class BaseFragment<B : ViewBinding> : Fragment() {
// Buradaki abstract fonksiyonları kullanarak, gerçek fragment'a geçince ihtiyacımız olan class'ları verecekler.

//    protected lateinit var userPreferences: UserPreferences

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
//        userPreferences = UserPreferences(requireContext())

//        lifecycleScope.launch {
//            userPreferences.authToken.first()
//        }

        return binding.root
    }


    abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B


}