package controller

import model.WikiSearchResponse
import service.WikiServiceInterface
import utils.DesktopUtils.openUrlInBrowser
import utils.StringUtils

class WikiController(private val wikiService: WikiServiceInterface) : WikiControllerInterface {
    override suspend fun performSearch(query: String) {
        val searchResult: WikiSearchResponse = wikiService.search(query)

        var i = 0;
        for (result in searchResult.query.search) {
            i++
            val cleanSnippet = StringUtils.removeHtmlTags(result.snippet)
            println("$i) Title: ${result.title}")
            println("Snippet: $cleanSnippet")
            println("Page ID: ${result.pageid}")
            println()
        }

        print("Какую страницу отркыть?: ")

        var input: Int = readlnOrNull()?.toInt() ?: 0
        while ((input <= 0) or (input > 10)) {
            println("Не корректное значение")
            print("Какую страницу отркыть?: ")
            input = readlnOrNull()?.toInt() ?: 0
        }

        val urlToOpen = "https://ru.wikipedia.org/w/index.php?curid=${searchResult.query.search[input-1].pageid}"
        openUrlInBrowser(urlToOpen)
    }
}