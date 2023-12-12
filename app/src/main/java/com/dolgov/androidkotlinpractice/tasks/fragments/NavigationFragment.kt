package com.dolgov.androidkotlinpractice.tasks.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dolgov.androidkotlinpractice.R
import com.dolgov.androidkotlinpractice.databinding.FragmentNavigationBinding
import com.dolgov.androidkotlinpractice.util.viewBinding

class NavigationFragment: Fragment(R.layout.fragment_navigation) {
    val viewBinding by viewBinding(FragmentNavigationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.butBack462.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}