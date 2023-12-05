package xyz.starchen.wincc.controller

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class DataController: Initializable {
    @FXML
    private var qualifiedQuantity = Label()
    @FXML
    private var qualificationRate = Label()
    @FXML
    private var unqualifiedQuantity = Label()
    @FXML
    private var total = Label()
    @FXML
    private var linkState = Label()
    @FXML
    private var runTime = Label()

    private fun startClock() {
        val createTime = Date()
        val clockThread = Thread {
            while (true) {
                val seconds = (Date().time - createTime.time) / 1000
                Platform.runLater {
                    this.runTime.text = "${seconds / 3600}h ${seconds % 3600 / 60}m ${seconds % 3600 % 60}s"
                }
                TimeUnit.SECONDS.sleep(1)
            }
        }
        clockThread.isDaemon = true
        clockThread.start()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.startClock()
    }
}