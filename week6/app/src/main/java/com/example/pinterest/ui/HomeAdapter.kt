package com.example.pinterest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest.ImageItem
import com.example.pinterest.databinding.ItemImageBinding

class HomeAdapter(
    private val items: List<ImageItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(items[position])
    }

    inner class HomeViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageItem) {
            binding.itemIv.setImageResource(item.image)
            binding.itemTv.text = item.title

            // 높이 설정
            val params: ViewGroup.LayoutParams = binding.itemIv.layoutParams
            params.height = item.height
            binding.itemIv.layoutParams = params
        }
    }
}