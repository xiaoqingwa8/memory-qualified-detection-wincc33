package xyz.starchen.wincc

import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import xyz.starchen.wincc.util.ResourceUtil

class MyApplication: Application() {
    override fun start(primaryStage: Stage) {
        val root: Parent = ResourceUtil.loadFxmlFile("/fxml/root_view.fxml")
        primaryStage.title = "内存弯曲度检测系统上位机"
        primaryStage.setScene(Scene(root, 1280.0, 720.0))
        primaryStage.isResizable = false
        primaryStage.show()
    }
}

fun main() {
    Application.launch(MyApplication::class.java)
}