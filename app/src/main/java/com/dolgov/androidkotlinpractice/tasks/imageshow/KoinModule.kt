package com.dolgov.androidkotlinpractice.tasks.imageshow

import android.app.NotificationManager
import androidx.core.content.getSystemService
import androidx.work.WorkManager
import com.dolgov.androidkotlinpractice.tasks.imageshow.model.ImageShowDispatchers
import com.dolgov.androidkotlinpractice.tasks.imageshow.model.ImageShowModel
import com.dolgov.androidkotlinpractice.tasks.imageshow.ui.ImageShowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun imageShowModule() =
    module {
        viewModelOf(::ImageShowViewModel)
        singleOf(::ImageShowModel)

        single<WorkManager> { WorkManager.getInstance(androidContext()) }
        single<NotificationManager> { androidContext().getSystemService()!! }
        singleOf(::ImageShowDispatchers)
    }