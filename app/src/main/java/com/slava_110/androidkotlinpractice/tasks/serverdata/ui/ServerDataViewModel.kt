package com.slava_110.androidkotlinpractice.tasks.serverdata.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.Answer
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.ServerDataDao
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class ServerDataViewModel(
    private val httpClient: HttpClient,
    private val yesNoDatabase: ServerDataDao
) : ViewModel() {
    private val _yesNoFlow = MutableStateFlow("Update me!")
    val yesNoFlow: StateFlow<String>
        get() = _yesNoFlow

    fun updateYesNo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpClient.get("https://yesno.wtf/api")

                if(response.status.isSuccess()) {
                    val answer = response.body<JsonObject>()["answer"]
                        ?.jsonPrimitive
                        ?.content
                        ?: "Error when tried to get answer!"

                    yesNoDatabase.insert(Answer(0, answer))

                    _yesNoFlow.emit(answer)
                }
            } catch (e: Exception) {
                _yesNoFlow.emit("Error on request!")
            }
        }
    }
}