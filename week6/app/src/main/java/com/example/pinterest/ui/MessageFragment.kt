package com.example.pinterest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pinterest.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }
}