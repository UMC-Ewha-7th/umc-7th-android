package com.example.myapplication.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.music
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var musicDatas = ArrayList<music>()

    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        musicDatas.apply{
            add(music("cloudy", "Mac Ayres", R.drawable.cloudy))
            add(music("Heartbreaker", "G-DRAGON", R.drawable.heartbreaker))
            add(music("One Of A Kind", "G-DRAGON", R.drawable.oneofakind))
            add(music("COUP D'ETAT", "G-DRAGON", R.drawable.coupdetat))
            add(music("권지용", "G-DRAGON", R.drawable.kjy))
            add(music("POWER", "G-DRAGON", R.drawable.power))
            add(music("Goblin", "Tyler, The Creator", R.drawable.goblin))
            add(music("Wolf", "Tyler, The Creator", R.drawable.wolf))
            add(music("Cherry Bomb", "Tyler, The Creator", R.drawable.cherrybomb))
            add(music("Flower Boy", "Tyler, The Creator", R.drawable.flowerboy))
            add(music("IGOR", "Tyler, The Creator", R.drawable.igor))
            add(music("CALL ME IF YOU GET LOST", "Tyler, The Creator", R.drawable.cmiygl))
            add(music("CHROMAKOPIA", "Tyler, The Creator", R.drawable.chromakopia))
            add(music("KILL THIS LOVE", "BLACK PINK", R.drawable.killthislove))
            add(music("How You Like That", "BLACK PINK", R.drawable.howyoulikethat))
            add(music("Ice Cream", "BLACK PINK", R.drawable.icecream))
            add(music("THE ALBUM", "BLACK PINK", R.drawable.thealbum))
            add(music("BORN PINK", "BLACK PINK", R.drawable.bornpink))

        }




        val adapter =HomeFragmentRVAdapter(musicDatas)
        binding.Rv.adapter = adapter
        binding.Rv.layoutManager =  GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)




        return root
    }



}