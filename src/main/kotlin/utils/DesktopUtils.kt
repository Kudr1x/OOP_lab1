package utils

import java.awt.Desktop
import java.net.URI

object DesktopUtils {
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
}