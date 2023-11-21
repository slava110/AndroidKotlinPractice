package com.slava_110.androidkotlinpractice.tasks.camera

import com.slava_110.androidkotlinpractice.tasks.camera.repository.CameraRepository
import com.slava_110.androidkotlinpractice.tasks.camera.ui.CameraListViewModel
import com.slava_110.androidkotlinpractice.tasks.camera.ui.CameraViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun cameraModule() =
    module {
        viewModelOf(::CameraViewModel)
        viewModelOf(::CameraListViewModel)
        single { CameraRepository(androidContext().filesDir) }
    }

