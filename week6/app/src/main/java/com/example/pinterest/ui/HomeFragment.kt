package com.example.pinterest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.ImageItem
import com.example.pinterest.R
import com.example.pinterest.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val items = ArrayList<ImageItem>()
        for (i in 0..10) {
            items.add(ImageItem(R.drawable.img_1))
            items.add(ImageItem(R.drawable.img_2, "Title $i"))
            items.add(ImageItem(R.drawable.img_3))
        }
        setupRecyclerView(items)

        return binding.root
    }

    private fun setupRecyclerView(items: ArrayList<ImageItem>) {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.homeRv.layoutManager = layoutManager
        homeAdapter = HomeAdapter(items)
        binding.homeRv.adapter = homeAdapter
    }
}