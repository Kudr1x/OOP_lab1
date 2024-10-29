import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
data class WikiSearchResponse(
    val query: Query
)

@Serializable
data class Query(
    val search: List<SearchResult>,
)

@Serializable
data class SearchResult(
    val title: String,
    val snippet: String,
    val pageid: Int
)

private val json = Json {
    ignoreUnknownKeys = true
}

fun removeHtmlTags(input: String): String {
    return input.replace(Regex("<[^>]*>"), "")
}

fun openUrlInBrowser(url: String) {
    if (Desktop.isDesktopSupported()) {
        try {
            val desktop = Desktop.getDesktop()
            desktop.browse(URI(url))
        } catch (e: Exception) {
            println("Ошибка при открытии URL: ${e.message}")
        }
    } else {
        println("Desktop не поддерживается, не удается открыть URL.")
    }
}

fun main() {
    runBlocking {
        val client = HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.INFO
            }
        }

        print("Введите запрос: ")
        val inp = readLine()?.trim() ?: ""

        val encodedSearch = URLEncoder.encode(inp, StandardCharsets.UTF_8)

        try {
            val response: HttpResponse = client
                .get("https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"${encodedSearch}\"") {
            }

            val responseBody = response.bodyAsText()

            val searchResult = json.decodeFromString<WikiSearchResponse>(responseBody)
            
            for (result in searchResult.query.search) {
                val cleanSnippet = removeHtmlTags(result.snippet)
                println("Title: ${result.title}")
                print("Snippet: $cleanSnippet\n")
                println("Snippet: ${result.pageid}")
                println()
            }

            val urlToOpen = "https://ru.wikipedia.org/w/index.php?curid=${searchResult.query.search[0].pageid}"

            openUrlInBrowser(urlToOpen)

        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        } finally {
            client.close()
        }
    }
}
