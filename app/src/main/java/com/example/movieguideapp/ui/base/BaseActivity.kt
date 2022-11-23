package com.example.movieguideapp.ui.base

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movieguideapp.data.local.model.ViewModelEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

abstract class BaseActivity<T> : AppCompatActivity() where T: BaseViewModel {

    private var loadingFragment: DialogLoadingFragment? = null
    private var baseViewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = initViewModel()
        baseViewModel?.let {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    it.loadingState.collect {
                        when (it) {
                            true -> showLoading()
                            else -> hideLoading()
                        }
                    }
                }

                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    it.eventFlow.collect { event ->
                        when (event) {
                            is ViewModelEvent.MessageShowing -> showMessageDialog(message = event.message)
                        }
                    }
                }
            }

        }
    }

    protected fun showLoading() {
        loadingFragment = DialogLoadingFragment()
        loadingFragment?.show(supportFragmentManager, DialogLoadingFragment.TAG)
    }

    protected fun hideLoading() {
        loadingFragment?.dismiss()
        loadingFragment = null
    }

    protected fun showMessageDialog(
        title: String = "Message",
        message: String,
        confirmAction: (dialog: DialogInterface) -> Unit = { dialog -> dialog.dismiss() }
    ) {
        MaterialAlertDialogBuilder(this)
            // Add customization options here
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Accept") { dialog, which ->
                confirmAction(dialog)
            }
            .show()
    }

    abstract fun initViewModel(): T
}