package com.dolgov.androidkotlinpractice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.dolgov.androidkotlinpractice.databinding.FragmentMainBinding
import com.dolgov.androidkotlinpractice.tasks.camera.ui.CameraFragment
import com.dolgov.androidkotlinpractice.tasks.compose.ui.ComposeMainFragment
import com.dolgov.androidkotlinpractice.tasks.fragments.NavigationFragment
import com.dolgov.androidkotlinpractice.tasks.imageshow.ui.ImageShowFragment
import com.dolgov.androidkotlinpractice.tasks.serverdata.ui.ServerDataFragment
import com.dolgov.androidkotlinpractice.util.viewBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    val viewBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.butNavigation.setOnClickListener {
            parentFragmentManager.commit {
                replace<NavigationFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
        viewBinding.butCamera.setOnClickListener {
            parentFragmentManager.commit {
                replace<CameraFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
        viewBinding.butServerData.setOnClickListener {
            parentFragmentManager.commit {
                replace<ServerDataFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
        viewBinding.butImageShow.setOnClickListener {
            parentFragmentManager.commit {
                replace<ImageShowFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
        viewBinding.butCompose.setOnClickListener {
            parentFragmentManager.commit {
                replace<ComposeMainFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }
    }
}