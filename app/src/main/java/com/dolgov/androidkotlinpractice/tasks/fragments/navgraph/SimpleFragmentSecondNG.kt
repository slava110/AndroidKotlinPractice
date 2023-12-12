package com.dolgov.androidkotlinpractice.tasks.fragments.navgraph

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dolgov.androidkotlinpractice.R
import com.dolgov.androidkotlinpractice.databinding.FragmentSimpleSecondBinding
import com.dolgov.androidkotlinpractice.util.viewBinding

class SimpleFragmentSecondNG : Fragment(R.layout.fragment_simple_second) {
    private val viewBinding by viewBinding(FragmentSimpleSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.buttonSec.setOnClickListener {
            findNavController().navigate(R.id.action_simpleFragmentSecond_to_simpleFragmentFirst)
        }
    }
}