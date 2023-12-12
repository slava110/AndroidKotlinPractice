package com.dolgov.androidkotlinpractice.tasks.camera

import com.dolgov.androidkotlinpractice.tasks.camera.model.CameraModel
import com.dolgov.androidkotlinpractice.tasks.camera.ui.CameraListViewModel
import com.dolgov.androidkotlinpractice.tasks.camera.ui.CameraViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun cameraModule() =
    module {
        viewModelOf(::CameraViewModel)
        viewModelOf(::CameraListViewModel)
        single { CameraModel(androidContext().filesDir) }
    }

