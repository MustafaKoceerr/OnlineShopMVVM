package com.kocerlabs.onlineshopmvvm.ui.cart

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kocerlabs.onlineshopmvvm.R
import com.kocerlabs.onlineshopmvvm.databinding.FragmentCartBinding
import com.kocerlabs.onlineshopmvvm.helper.ChangeNumberItemsListener
import com.kocerlabs.onlineshopmvvm.helper.ManagementCart
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseFragment


class CartFragment : BaseFragment<FragmentCartBinding>() {

    private lateinit var managementCart: ManagementCart
    private var tax: Double = 0.0

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managementCart = ManagementCart(requireContext())

        setVariable()
        initCartList()
        calculatorCart()
    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener {
                val action = CartFragmentDirections.actionCartFragmentToDashboardFragment()
                findNavController().navigate(action)
            }

            method1.setOnClickListener {
                method1.setBackgroundResource(R.drawable.green_bg_selected)
                methodIc1.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                methodTitle1.setTextColor(resources.getColor(R.color.green))
                methodSubtitle1.setTextColor(resources.getColor(R.color.green))

                method2.setBackgroundResource(R.drawable.grey_bg_selected)
                methodIc2.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
                methodTitle2.setTextColor(resources.getColor(R.color.black))
                methodSubtitle2.setTextColor(resources.getColor(R.color.grey))
            }

            method2.setOnClickListener {
                method1.setBackgroundResource(R.drawable.grey_bg_selected)
                methodIc1.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
                methodTitle1.setTextColor(resources.getColor(R.color.black))
                methodSubtitle1.setTextColor(resources.getColor(R.color.grey))

                method2.setBackgroundResource(R.drawable.green_bg_selected)
                methodIc2.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                methodTitle2.setTextColor(resources.getColor(R.color.green))
                methodSubtitle2.setTextColor(resources.getColor(R.color.green))
            }
        }
    }


    private fun initCartList() {
        with(binding) {
            viewCart.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            viewCart.adapter = CartAdapter(
                managementCart.getListCart(),
                requireContext(),
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculatorCart()
                    }
                })

            emptyTxt.visibility =
                if (managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView.visibility =
                if (managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }


    private fun calculatorCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managementCart.getTotalFee() * percentTax * 100)) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100

        with(binding) {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }


}