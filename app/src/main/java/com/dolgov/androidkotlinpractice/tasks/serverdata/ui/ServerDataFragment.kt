package com.dolgov.androidkotlinpractice.tasks.serverdata.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.dolgov.androidkotlinpractice.R
import com.dolgov.androidkotlinpractice.databinding.FragmentServerDataBinding
import com.dolgov.androidkotlinpractice.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServerDataFragment : Fragment(R.layout.fragment_server_data) {
    private val viewModel: ServerDataViewModel by viewModel<ServerDataViewModel>()
    private val viewBinding by viewBinding(FragmentServerDataBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.yesNoFlow.collectLatest {
                viewBinding.textView.text = it
            }
        }
        viewBinding.button.setOnClickListener {
            viewModel.updateYesNo()
        }
        viewBinding.buttonDbList.setOnClickListener {
            parentFragmentManager.commit {
                replace<ServerDataListFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
        viewBinding.backButton4.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}