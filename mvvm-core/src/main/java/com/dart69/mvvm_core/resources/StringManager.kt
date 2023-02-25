package com.dart69.mvvm_core.resources

import android.content.Context
import androidx.annotation.StringRes

interface StringManager {
    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String

    class Default(
        private val context: Context
    ) : StringManager {

        override fun getString(
            @StringRes stringRes: Int,
            vararg formatArgs: Any
        ): String = context.getString(stringRes, formatArgs)
    }
}