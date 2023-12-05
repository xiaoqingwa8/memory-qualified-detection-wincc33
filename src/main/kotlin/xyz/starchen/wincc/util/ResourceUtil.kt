package xyz.starchen.wincc.util

import javafx.fxml.FXMLLoader

object ResourceUtil {
    fun <T> loadFxmlFile(path: String): T {
        val file = javaClass.getResource(path)
        return FXMLLoader.load(file)
    }
}
