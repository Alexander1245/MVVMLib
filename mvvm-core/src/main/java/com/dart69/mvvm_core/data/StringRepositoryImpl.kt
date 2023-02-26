package com.dart69.mvvm_core.data

import android.content.Context
import com.dart69.mvvm_core.domain.StringRepository

class StringRepositoryImpl(
    private val context: Context
) : StringRepository {
    override fun getString(stringRes: Int, vararg formatArgs: Any): String =
        context.getString(stringRes, formatArgs)
}