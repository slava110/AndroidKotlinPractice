package com.dolgov.androidkotlinpractice.tasks.imageshow.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dolgov.androidkotlinpractice.R
import com.dolgov.androidkotlinpractice.databinding.FragmentImageShowBinding
import com.dolgov.androidkotlinpractice.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageShowFragment : Fragment(R.layout.fragment_image_show) {
    private val viewModel: ImageShowViewModel by viewModel<ImageShowViewModel>()
    private val viewBinding by viewBinding(FragmentImageShowBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.buttonSet.setOnClickListener {
            viewModel.downloadImage(viewBinding.urlEdit.text.toString())
        }

        lifecycleScope.launch {
            viewModel.imageState.collectLatest { state ->
                when(state) {
                    ImageShowState.Default -> {
                        viewBinding.imageView.setImageResource(R.drawable.question_mark)
                    }
                    is ImageShowState.Loading -> {
                        viewBinding.imageView.setImageResource(R.drawable.question_mark)
                    }
                    is ImageShowState.Complete -> {
                        viewBinding.imageView.setImageURI(Uri.parse(state.outputImageUri))
                    }
                    is ImageShowState.Errored -> {
                        viewBinding.imageView.setImageResource(R.drawable.question_mark)
                    }
                }
            }
        }

        viewBinding.buttonBack23.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}