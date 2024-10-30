package main

import controller.WikiController
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import service.WikiService

const val DEBUG: Boolean = false

fun main() = runBlocking {
    val client = HttpClient(CIO) {
        if(DEBUG){
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }

    val json = Json {
        ignoreUnknownKeys = true
    }

    val wikiService = WikiService(client, json)
    val wikiController = WikiController(wikiService)

    print("Введите запрос: ")
    val input = readlnOrNull()?.trim() ?: ""

    try {
        wikiController.performSearch(input)
    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
    } finally {
        client.close()
    }
}
