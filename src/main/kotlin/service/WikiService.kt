package service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import model.WikiSearchResponse
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class WikiService(private val client: HttpClient, private val json: Json) : WikiServiceInterface {
    override suspend fun search(query: String): WikiSearchResponse {
        val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8)
        val response: HttpResponse = client.get(
            "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"$encodedQuery\""
        ) {
            contentType(ContentType.Application.Json)
        }

        return json.decodeFromString(response.bodyAsText())
    }
}