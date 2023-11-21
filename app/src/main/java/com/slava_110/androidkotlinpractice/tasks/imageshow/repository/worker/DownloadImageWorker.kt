package com.slava_110.androidkotlinpractice.tasks.imageshow.repository.worker

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.slava_110.androidkotlinpractice.tasks.imageshow.repository.ImageShowDispatchers
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class DownloadImageWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {
    private val dispatchers by inject<ImageShowDispatchers>()
    private val httpClient by inject<HttpClient>()

    override suspend fun doWork(): Result =
        withContext(dispatchers.networkDispatcher) {
            try {
                val url = inputData.getString("imageUrl")
                    ?: throw IllegalArgumentException("No URL was provided!")

                val response = httpClient.get(url) {
                    contentType(ContentType.Image.Any)
                    onDownload { bytesSentTotal, contentLength ->
                        setProgress(
                            workDataOf(
                                "progressMade" to bytesSentTotal,
                                "progressTotal" to contentLength
                            )
                        )
                    }
                }

                if(response.status.isSuccess()) {
                    withContext(dispatchers.diskDispatcher) {
                        val outputFile = File.createTempFile(
                            "downloadedImage",
                            null,
                            applicationContext.cacheDir
                        ).also { file ->
                            response.bodyAsChannel().copyAndClose(file.writeChannel())
                        }

                        Result.success(
                            workDataOf(
                                "outputImage" to outputFile.toUri().toString()
                            )
                        )
                    }
                } else {
                    Result.failure(
                        workDataOf(
                            "error" to response.status.description
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error during downloading image", e)
                Result.failure(
                    workDataOf(
                        "error" to e.message
                    )
                )
            }
        }

    companion object {
        const val LOG_TAG = "DownloadImageWorker"
    }
}