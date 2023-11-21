package com.slava_110.androidkotlinpractice.tasks.imageshow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.slava_110.androidkotlinpractice.tasks.imageshow.repository.ImageShowRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ImageShowViewModel(
    private val repository: ImageShowRepository
): ViewModel() {
    val imageState: StateFlow<ImageShowState> =
        repository.outputWorkInfo.map {
            when(it.state) {
                WorkInfo.State.RUNNING -> {
                    ImageShowState.Loading(
                        it.progress.getInt("progressMade", 0),
                        it.progress.getInt("progressTotal", 0)
                    )
                }
                WorkInfo.State.SUCCEEDED -> {
                    ImageShowState.Complete(it.outputData.getString("outputImage")
                        ?: throw RuntimeException("Output file was not specified!")
                    )
                }
                WorkInfo.State.FAILED -> {
                    ImageShowState.Errored(it.outputData.getString("error")
                        ?: "Unknown error"
                    )
                }
                else -> ImageShowState.Default
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ImageShowState.Default
        )

    fun downloadImage(url: String) {
        repository.downloadImage(url)
    }

    fun cancelDownloading() {
        repository.cancelDownloading()
    }
}

sealed interface ImageShowState {
    data object Default: ImageShowState
    data class Loading(val progressMade: Int, val progressTotal: Int): ImageShowState
    data class Complete(val outputImageUri: String): ImageShowState
    data class Errored(val error: String): ImageShowState
}