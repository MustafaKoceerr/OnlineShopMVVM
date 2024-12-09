package com.kocerlabs.onlineshopmvvm.ui.listitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kocerlabs.onlineshopmvvm.data.network.model.ProductsModel
import com.kocerlabs.onlineshopmvvm.databinding.ViewholderRecommendedBinding

class ListItemsAdapter(
    val items: List<ProductsModel>,
    val callback: (item: ProductsModel) -> Unit
) :
    RecyclerView.Adapter<ListItemsAdapter.RecommendedViewHolder>() {
    private val TAG = "RecommendedAdapter"

    inner class RecommendedViewHolder(val recommendedBinding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(recommendedBinding.root) {

        fun bind(item: ProductsModel) {
            with(recommendedBinding) {
                titleTxt.text = item.title
                priceTxt.text = "$${item.price}"
                ratingTxt.text = item.rating.toString()
                Glide.with(itemView.context)
                    .load(item.picUrl[0])
                    .into(pic)

                root.setOnClickListener {
                    callback(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemsAdapter.RecommendedViewHolder = RecommendedViewHolder(
        ViewholderRecommendedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ListItemsAdapter.RecommendedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}