package com.dart69.mvvm_core.presentation.screens

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dart69.mvvm_core.coroutines.LifecycleCollector
import com.dart69.mvvm_core.presentation.viewmodels.BaseViewModel

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<*>>(
    @LayoutRes layoutRes: Int
) : AppCompatActivity(layoutRes), LifecycleCollector by LifecycleCollector.Default() {
    protected abstract val viewBinding: VB
    protected abstract val viewModel: VM
}