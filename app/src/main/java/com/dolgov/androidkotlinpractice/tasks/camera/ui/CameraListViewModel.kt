package com.dolgov.androidkotlinpractice.tasks.camera.ui

import androidx.lifecycle.ViewModel
import com.dolgov.androidkotlinpractice.tasks.camera.model.CameraModel

class CameraListViewModel(
    private val model: CameraModel
): ViewModel() {

    suspend fun getDates(): List<String> =
        model.getDates()
}