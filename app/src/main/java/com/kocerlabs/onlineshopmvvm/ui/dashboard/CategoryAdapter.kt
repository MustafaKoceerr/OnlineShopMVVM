package com.kocerlabs.onlineshopmvvm.ui.dashboard

import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kocerlabs.onlineshopmvvm.R
import com.kocerlabs.onlineshopmvvm.data.network.model.CategoryModel
import com.kocerlabs.onlineshopmvvm.databinding.ViewholderCategoryBinding

class CategoryAdapter(
    val items: List<CategoryModel>,
    val callback: (id: String, title: String) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val TAG = "CategoryAdapter"
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class CategoryViewHolder(val categoryBinding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(categoryBinding.root) {


        fun bind(categoryModel: CategoryModel) {
            with(categoryBinding) {
                titleTxt.text = categoryModel.title

                Glide.with(itemView.context)
                    .load(categoryModel.picUrl)
                    .into(pic)

                if (selectedPosition == position) {
                    pic.setBackgroundResource(0)
                    mainLayout.setBackgroundResource(R.drawable.green_button_bg)
                    ImageViewCompat.setImageTintList(
                        pic,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.white
                            )
                        )
                    )
                    titleTxt.visibility = View.VISIBLE
                    titleTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                } else {
                    pic.setBackgroundResource(R.drawable.grey_bg)
                    mainLayout.setBackgroundResource(0)
                    ImageViewCompat.setImageTintList(
                        pic,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.black
                            )
                        )
                    )
                    titleTxt.visibility = View.GONE
                    titleTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ViewholderCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])

        holder.categoryBinding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                callback(items[position].id.toString(), items[position].title)
            }, 1000)
        }

    }


}