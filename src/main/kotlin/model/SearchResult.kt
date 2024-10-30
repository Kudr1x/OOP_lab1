package model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val title: String,
    val snippet: String,
    val pageid: Int
)


