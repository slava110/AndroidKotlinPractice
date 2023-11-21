package com.slava_110.androidkotlinpractice.tasks.fragments.navgraph

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentSimpleFirstBinding
import com.slava_110.androidkotlinpractice.util.viewBinding

class SimpleFragmentFirstNG : Fragment(R.layout.fragment_simple_first) {
    private val viewBinding by viewBinding(FragmentSimpleFirstBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_simpleFragmentFirst_to_simpleFragmentSecond)
        }
    }
}
