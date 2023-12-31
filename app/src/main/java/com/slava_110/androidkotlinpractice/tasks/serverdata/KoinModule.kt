package com.slava_110.androidkotlinpractice.tasks.serverdata

import androidx.room.Room
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.ServerDataDao
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.ServerDataDatabase
import com.slava_110.androidkotlinpractice.tasks.serverdata.ui.ServerDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.dsl.module
import org.koin.dsl.onClose

fun serverDataModule() =
    module {
        single {
            Room.databaseBuilder(
                androidContext(),
                ServerDataDatabase::class.java,
                "serverdata"
            ).build()
        } onClose { it?.close() }

        single<ServerDataDao> {
            get<ServerDataDatabase>().getServerDataDao()
        }

        viewModelOf(::ServerDataViewModel)
    }