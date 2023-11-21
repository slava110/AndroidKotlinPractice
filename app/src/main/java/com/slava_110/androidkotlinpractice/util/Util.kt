package com.slava_110.androidkotlinpractice.util

import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import kotlin.reflect.typeOf

inline fun <reified S: Any> Fragment.systemService() =
    lazy {
        requireContext().applicationContext.getSystemService<S>()
            ?: throw RuntimeException("Service `${ typeOf<S>() }` not found!")
    }