package com.dart69.mvvm_core.presentation.screens

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dart69.mvvm_core.presentation.viewmodels.BaseViewModel

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<*>>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes) {
    protected abstract val viewBinding: VB
    protected abstract val viewModel: VM
}