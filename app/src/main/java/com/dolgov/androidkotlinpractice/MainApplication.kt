package com.dolgov.androidkotlinpractice

import android.app.Application
import com.dolgov.androidkotlinpractice.tasks.camera.cameraModule
import com.dolgov.androidkotlinpractice.tasks.imageshow.imageShowModule
import com.dolgov.androidkotlinpractice.tasks.serverdata.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.dsl.onClose
import kotlin.time.Duration.Companion.seconds

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(
                cameraModule(),
                serverDataModule(),
                imageShowModule(),
                module {
                    single<HttpClient> {
                        HttpClient(Android) {
                            install(ContentNegotiation) {
                                json()
                            }
                            install(HttpTimeout) {
                                requestTimeoutMillis = 1.seconds.inWholeMilliseconds
                            }
                        }
                    } onClose { it?.close() }
                }
            )
        }
    }
}