package com.slava_110.androidkotlinpractice.tasks.fragments.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentSimpleFirstBinding
import com.slava_110.androidkotlinpractice.util.viewBinding

class SimpleFragmentFirstTR : Fragment(R.layout.fragment_simple_first) {
    private val viewBinding by viewBinding(FragmentSimpleFirstBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.buttonFirst.setOnClickListener {
            parentFragmentManager.commit {
                replace<SimpleFragmentSecondTR>(R.id.fragmentContainerNavigationTransaction)
            }
        }
    }
}