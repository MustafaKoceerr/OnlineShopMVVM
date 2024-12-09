package com.kocerlabs.onlineshopmvvm.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.databinding.ViewholderCartBinding
import com.kocerlabs.onlineshopmvvm.helper.ChangeNumberItemsListener
import com.kocerlabs.onlineshopmvvm.helper.ManagementCart

class CartAdapter(
    private val listItemSelected: ArrayList<ProductsModel>,
    val context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener,
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val cartBinding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(cartBinding.root) {

        fun bind(item: ProductsModel) {
            with(cartBinding) {
                titleTxt.text = item.title
                feeEachTime.text = "$${item.price}"
                totalEachItem.text = "$${Math.round(item.numberInCart * item.price)}"
                numberItemTxt.text = item.numberInCart.toString()

                Glide.with(itemView.context)
                    .load(item.picUrl[0])
                    .into(pic)

                plusCartBtn.setOnClickListener(::plusItem)
                minusCartBtn.setOnClickListener(::minusItem)
            }

        }

        fun plusItem(view: View) {
            val managementCart = ManagementCart(context)
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }

        fun minusItem(view: View) {
            val managementCart = ManagementCart(context)
            managementCart.minusItem(
                listItemSelected,
                position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.onChanged()
                    }
                })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ViewholderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listItemSelected.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(listItemSelected[position])
    }

}