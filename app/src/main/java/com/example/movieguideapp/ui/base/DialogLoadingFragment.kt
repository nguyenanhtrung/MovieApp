package com.example.movieguideapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.movieguideapp.databinding.FragmentLoadingDialogBinding

class DialogLoadingFragment: DialogFragment() {

    companion object {
        const val TAG = "DialogLoadingFragment"
    }

    private lateinit var binding: FragmentLoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
}