package controller

interface WikiControllerInterface {
    suspend fun performSearch(query: String)
}