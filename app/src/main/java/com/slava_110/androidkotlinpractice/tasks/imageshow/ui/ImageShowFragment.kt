package com.slava_110.androidkotlinpractice.tasks.imageshow.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentImageShowBinding
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowState
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowViewModel
import com.slava_110.androidkotlinpractice.util.viewBinding
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
            viewModel.imageState.collectLatest {
                when(it) {
                    ImageShowState.Default -> {
                        viewBinding.imageView.setImageResource(R.drawable.question_mark)
                    }
                    is ImageShowState.Loading -> {
                        viewBinding.imageView.setImageResource(R.drawable.question_mark)
                    }
                    is ImageShowState.Complete -> {
                        viewBinding.imageView.setImageURI(Uri.parse(it.outputImageUri))
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