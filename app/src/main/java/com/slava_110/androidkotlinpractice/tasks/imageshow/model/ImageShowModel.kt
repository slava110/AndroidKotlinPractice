package com.slava_110.androidkotlinpractice.tasks.imageshow.model

import androidx.lifecycle.asFlow
import androidx.work.*
import com.slava_110.androidkotlinpractice.tasks.imageshow.model.worker.DownloadImageWorker
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ImageShowModel: KoinComponent {
    private val workManager by inject<WorkManager>()

    val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdLiveData(outputTaskId).asFlow()

    fun downloadImage(url: String) {
        workManager.enqueueUniqueWork(
            "DownloadImage",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.Builder(DownloadImageWorker::class.java).apply {
                setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresStorageNotLow(true)
                        .build()
                )
                setInputData(
                    workDataOf(
                        "imageUrl" to url
                    )
                )
                setId(outputTaskId)
            }.build()
        )
    }

    companion object {
        val outputTaskId: UUID = UUID.fromString("82c297ee-2542-4996-869e-34de66e1c003")
    }
}