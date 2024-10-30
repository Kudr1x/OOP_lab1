package model

import kotlinx.serialization.Serializable

@Serializable
data class WikiSearchResponse(
    val query: Query
)