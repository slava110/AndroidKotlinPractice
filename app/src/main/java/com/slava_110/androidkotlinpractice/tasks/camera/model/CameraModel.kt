package com.slava_110.androidkotlinpractice.tasks.camera.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraModel(filesDir: File) {
    private val photosDir = filesDir.resolve("photos")
    private val datesFile = filesDir.resolve("dates.txt")

    suspend fun saveImage(imageBytes: ByteArray) {
        withContext(Dispatchers.IO) {
            photosDir.writeBytes(imageBytes)
            datesFile.appendText("\n${ dateFormat.format(Date()) }")
        }
    }

    suspend fun getDates(): List<String> =
        withContext(Dispatchers.IO) {
            if(datesFile.exists())
                datesFile.readLines()
            else
                emptyList()
        }

    companion object {
        private val dateFormat = SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.ROOT)
    }
}