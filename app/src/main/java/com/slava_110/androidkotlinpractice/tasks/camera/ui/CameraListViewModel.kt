package com.slava_110.androidkotlinpractice.tasks.camera.ui

import androidx.lifecycle.ViewModel
import com.slava_110.androidkotlinpractice.tasks.camera.repository.CameraRepository

class CameraListViewModel(
    private val model: CameraRepository
): ViewModel() {

    suspend fun getDates(): List<String> =
        model.getDates()
}