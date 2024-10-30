package controller

import model.WikiSearchResponse
import service.WikiService
import utils.DesktopUtils.openUrlInBrowser
import utils.StringUtils

class WikiController(private val wikiService: WikiService) {

    suspend fun performSearch(query: String) {
        val searchResult: WikiSearchResponse = wikiService.search(query)

        for (result in searchResult.query.search) {
            val cleanSnippet = StringUtils.removeHtmlTags(result.snippet)
            println("Title: ${result.title}")
            println("Snippet: $cleanSnippet")
            println("Page ID: ${result.pageid}")
            println()
        }

        val urlToOpen = "https://ru.wikipedia.org/w/index.php?curid=${searchResult.query.search[0].pageid}"
        openUrlInBrowser(urlToOpen)
    }
}