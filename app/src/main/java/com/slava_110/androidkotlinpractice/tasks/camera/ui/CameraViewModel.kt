package com.slava_110.androidkotlinpractice.tasks.camera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slava_110.androidkotlinpractice.tasks.camera.repository.CameraRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraViewModel(
    private val model: CameraRepository
) : ViewModel() {

    fun saveImage(bytes: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            model.saveImage(bytes)
        }
    }
}