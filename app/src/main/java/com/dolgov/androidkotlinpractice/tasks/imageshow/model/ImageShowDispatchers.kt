package com.dolgov.androidkotlinpractice.tasks.imageshow.model

import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class ImageShowDispatchers: AutoCloseable {
    val networkDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val diskDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    override fun close() {
        networkDispatcher.close()
        diskDispatcher.close()
    }
}