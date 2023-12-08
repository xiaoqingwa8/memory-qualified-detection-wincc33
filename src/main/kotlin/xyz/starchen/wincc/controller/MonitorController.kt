package xyz.starchen.wincc.controller

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import xyz.starchen.wincc.service.impl.MemoryServiceImpl
import xyz.starchen.wincc.util.SerialReceiveDataListener
import xyz.starchen.wincc.util.SerialUtil
import java.net.URL
import java.util.*

class MonitorController:Initializable {
    @FXML
    private lateinit var difference1: Label
    @FXML
    private lateinit var difference2: Label
    @FXML
    private lateinit var difference3: Label
    @FXML
    private lateinit var difference4: Label
    @FXML
    private lateinit var difference5: Label
    @FXML
    private lateinit var difference6: Label
    @FXML
    private lateinit var difference7: Label
    @FXML
    private lateinit var difference8: Label
    @FXML
    private lateinit var difference9: Label
    @FXML
    private lateinit var difference10: Label
    @FXML
    private lateinit var difference11: Label
    @FXML
    private lateinit var difference12: Label

    @FXML
    private lateinit var showRegion1: Region
    @FXML
    private lateinit var showRegion2: Region
    @FXML
    private lateinit var showRegion3: Region
    @FXML
    private lateinit var showRegion4: Region
    @FXML
    private lateinit var showRegion5: Region
    @FXML
    private lateinit var showRegion6: Region
    @FXML
    private lateinit var showRegion7: Region
    @FXML
    private lateinit var showRegion8: Region
    @FXML
    private lateinit var showRegion9: Region
    @FXML
    private lateinit var showRegion10: Region
    @FXML
    private lateinit var showRegion11: Region
    @FXML
    private lateinit var showRegion12: Region

    private lateinit var showRegions: Array<Region>
    private lateinit var differences: Array<Label>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.showRegions = arrayOf(showRegion1, showRegion2, showRegion3, showRegion4,
                                   showRegion5, showRegion6, showRegion7, showRegion8,
                                   showRegion9, showRegion10, showRegion11, showRegion12)

        this.differences = arrayOf(difference1, difference2, difference3, difference4,
                                   difference5, difference6, difference7, difference8,
                                   difference9, difference10, difference11, difference12)

        for (region in this.showRegions) {
            region.background = Background.fill(Color.GRAY)
        }

        SerialUtil.addSerialReceiveDataListener(object: SerialReceiveDataListener {
            override fun notice() {
                updateDifferenceData()
            }
        })

        updateDifferenceData()
    }

    fun updateDifferenceData() {
        val memoryService = MemoryServiceImpl()
        val lastData = memoryService.selectLastData()
        val byCheckTime = memoryService.selectMemoryByCheckTime(lastData.checkTime, lastData.checkTime)
        for (i in differences.indices) {
            if (i <= byCheckTime.lastIndex) {
                differences[i].text = byCheckTime[i].difference.toString()
                if (byCheckTime[i].qualified) {
                    showRegions[i].background = Background.fill(Color.GREEN)
                } else {
                    showRegions[i].background = Background.fill(Color.RED)
                }
            } else {
                showRegions[i].background = Background.fill(Color.GRAY)
                differences[i].text = "0"
            }
        }
    }
}