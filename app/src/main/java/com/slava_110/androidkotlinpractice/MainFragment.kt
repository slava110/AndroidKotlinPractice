package com.slava_110.androidkotlinpractice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.androidkotlinpractice.databinding.FragmentMainBinding
import com.slava_110.androidkotlinpractice.tasks.camera.ui.CameraFragment
import com.slava_110.androidkotlinpractice.tasks.compose.ui.ComposeMainFragment
import com.slava_110.androidkotlinpractice.tasks.fragments.NavigationFragment
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowFragment
import com.slava_110.androidkotlinpractice.tasks.serverdata.ui.ServerDataFragment
import com.slava_110.androidkotlinpractice.util.viewBinding

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