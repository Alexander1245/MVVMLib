package com.dart69.mvvm_core.screens

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dart69.mvvm_core.coroutines.LifecycleCollector
import com.dart69.mvvm_core.viewmodels.BaseViewModel

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<*>>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes), LifecycleCollector by LifecycleCollector.Default() {
    protected abstract val viewBinding: VB
    protected abstract val viewModel: VM
}