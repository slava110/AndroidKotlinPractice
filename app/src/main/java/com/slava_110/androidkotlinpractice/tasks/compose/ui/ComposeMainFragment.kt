package com.slava_110.androidkotlinpractice.tasks.compose.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.slava_110.androidkotlinpractice.tasks.compose.ui.theme.AndroidKotlinPracticeTheme
import org.koin.compose.KoinContext

class ComposeMainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            KoinContext {
                AndroidKotlinPracticeTheme {
                    AppMainComposeScreen(
                        onExit = { parentFragmentManager.popBackStack() }
                    )
                }
            }
        }
    }
}

