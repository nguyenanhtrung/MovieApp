package com.example.movieguideapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {
    private var viewModel: BaseViewModel? = null

    private var activityViewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        activityViewModel = bindActivityViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel != null && activityViewModel != null) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.loadingState.collect{isLoading ->
                        when(isLoading) {
                            true -> activityViewModel!!.showloading()
                            else -> activityViewModel!!.hideLoading()
                        }
                    }
                }

                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.eventFlow.collect {
                        activityViewModel!!.sendEvent(it)
                    }
                }
            }
        }

    }

    abstract fun initViewModel(): BaseViewModel
    abstract fun bindActivityViewModel(): BaseViewModel
}