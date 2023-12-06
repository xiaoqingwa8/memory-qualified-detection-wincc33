package xyz.starchen.wincc.controller

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import xyz.starchen.wincc.util.SerialUtil
import java.net.URL
import java.util.*

class ToolsController: Initializable {
    @FXML
    private var serialChoice = ChoiceBox<String>()
    @FXML
    private var outputAllButton = Button()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        serialChoice.items.addAll(SerialUtil.findSerial())
        serialChoice.setOnAction {
            SerialUtil.openSerial(serialChoice.value)
        }
        if (!serialChoice.items.isEmpty()) {
            serialChoice.value = serialChoice.items.first()
        }
    }
}