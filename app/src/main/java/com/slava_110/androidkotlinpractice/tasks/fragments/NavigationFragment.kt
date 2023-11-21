package com.slava_110.androidkotlinpractice.tasks.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentNavigationBinding
import com.slava_110.androidkotlinpractice.util.viewBinding

class NavigationFragment: Fragment(R.layout.fragment_navigation) {
    val viewBinding by viewBinding(FragmentNavigationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.butBack462.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}