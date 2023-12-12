package com.dolgov.androidkotlinpractice.tasks.camera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dolgov.androidkotlinpractice.tasks.camera.model.CameraModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraViewModel(
    private val model: CameraModel
) : ViewModel() {

    fun saveImage(bytes: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            model.saveImage(bytes)
        }
    }
}