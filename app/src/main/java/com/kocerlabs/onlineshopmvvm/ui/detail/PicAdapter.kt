package com.kocerlabs.onlineshopmvvm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kocerlabs.onlineshopmvvm.R
import com.kocerlabs.onlineshopmvvm.databinding.ViewholderPicBinding

class PicAdapter(val items: List<String>, private val onImageSelected: (String) -> Unit) :
    RecyclerView.Adapter<PicAdapter.PicViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1


    inner class PicViewHolder(val picBinding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(picBinding.root) {

        fun bind(item: String) {
            with(picBinding) {
                pic.loadImage(item)

                root.setOnClickListener {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)

                    onImageSelected(item)
                }

                if (selectedPosition == position) {
                    picLayout.setBackgroundResource(R.drawable.green_bg_selected)
                } else {
                    picLayout.setBackgroundResource(R.drawable.grey_bg)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder =
        PicViewHolder(
            ViewholderPicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}