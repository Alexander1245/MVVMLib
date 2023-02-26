package com.dart69.mvvm_core.domain

interface StringRepository {
    fun getString(stringRes: Int, vararg formatArgs: Any): String
}