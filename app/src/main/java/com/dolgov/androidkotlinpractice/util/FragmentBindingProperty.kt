package com.dolgov.androidkotlinpractice.util

import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ViewBinding as delegate
 * @param provider generated bind function (for example FragmentServerDataBinding::bind)
 */
fun <T: ViewBinding> Fragment.viewBinding(
    provider: (View) -> T
): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        var binding: T? = null

        @MainThread
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: provider(requireView()).also {
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }
