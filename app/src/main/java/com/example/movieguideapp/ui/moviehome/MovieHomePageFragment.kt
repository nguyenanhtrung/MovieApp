package com.example.movieguideapp.ui.moviehome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieguideapp.R
import com.example.movieguideapp.databinding.FragmentMovieHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieHomePageFragment : Fragment() {

    private lateinit var binding: FragmentMovieHomePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }


}