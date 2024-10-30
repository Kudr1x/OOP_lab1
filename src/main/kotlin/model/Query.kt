package model

import kotlinx.serialization.Serializable

@Serializable
data class Query(
    val search: List<SearchResult>,
)