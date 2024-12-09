package com.kocerlabs.onlineshopmvvm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kocerlabs.onlineshopmvvm.R
import com.kocerlabs.onlineshopmvvm.databinding.ViewholderModelBinding

class SelectModelAdapter(val items: List<String>) :
    RecyclerView.Adapter<SelectModelAdapter.SelectModelViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class SelectModelViewHolder(val selectModelBinding: ViewholderModelBinding) :
        RecyclerView.ViewHolder(selectModelBinding.root) {
        fun bind(item: String) {
            with(selectModelBinding) {
                modelTxt.text = item
                root.setOnClickListener {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }

                if (selectedPosition == position) {
                    modelLayout.setBackgroundResource(R.drawable.green_bg_selected)
                    modelTxt.setTextColor(itemView.context.getColor(R.color.green))
                } else {
                    modelLayout.setBackgroundResource(R.drawable.grey_bg)
                    modelTxt.setTextColor(itemView.context.getColor(R.color.black))
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectModelViewHolder =
        SelectModelViewHolder(
            ViewholderModelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SelectModelViewHolder, position: Int) {
        holder.bind(items[position])
    }

}