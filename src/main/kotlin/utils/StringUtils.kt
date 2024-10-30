package utils


object StringUtils{
    fun removeHtmlTags(input: String): String {
        return input.replace(Regex("<[^>]*>"), "")
    }
}