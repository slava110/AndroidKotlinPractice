package com.slava_110.androidkotlinpractice.tasks.fragments.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentSimpleSecondBinding
import com.slava_110.androidkotlinpractice.util.viewBinding

class SimpleFragmentSecondTR : Fragment(R.layout.fragment_simple_second) {
    private val viewBinding by viewBinding(FragmentSimpleSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.buttonSec.setOnClickListener {
            parentFragmentManager.commit {
                replace<SimpleFragmentFirstTR>(R.id.fragmentContainerNavigationTransaction)
            }
        }
    }
}