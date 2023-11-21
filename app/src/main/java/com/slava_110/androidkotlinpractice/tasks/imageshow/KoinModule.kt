package com.slava_110.androidkotlinpractice.tasks.imageshow

import android.app.NotificationManager
import androidx.core.content.getSystemService
import androidx.work.WorkManager
import com.slava_110.androidkotlinpractice.tasks.imageshow.repository.ImageShowDispatchers
import com.slava_110.androidkotlinpractice.tasks.imageshow.repository.ImageShowRepository
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun imageShowModule() =
    module {
        viewModelOf(::ImageShowViewModel)
        singleOf(::ImageShowRepository)

        single<WorkManager> { WorkManager.getInstance(androidContext()) }
        single<NotificationManager> { androidContext().getSystemService()!! }
        singleOf(::ImageShowDispatchers)
    }