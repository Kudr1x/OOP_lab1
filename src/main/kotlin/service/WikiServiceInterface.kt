package service

import model.WikiSearchResponse

interface WikiServiceInterface {
    suspend fun search(query: String): WikiSearchResponse
}