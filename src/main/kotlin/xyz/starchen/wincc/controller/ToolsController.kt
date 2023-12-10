package xyz.starchen.wincc.controller

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import xyz.starchen.wincc.pojo.MemoryData
import xyz.starchen.wincc.service.impl.MemoryServiceImpl
import xyz.starchen.wincc.util.SaveFileUtil
import xyz.starchen.wincc.util.SerialUtil
import java.net.URL
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*


class ToolsController : Initializable {
    @FXML
    private var serialChoice = ChoiceBox<String>()

    @FXML
    private lateinit var startDatePicker: DatePicker

    @FXML
    private lateinit var endDatePicker: DatePicker

    @FXML
    private lateinit var radioButton1: RadioButton

    @FXML
    private lateinit var radioButton2: RadioButton

    @FXML
    private var toggleGroup = ToggleGroup()

    //判断选择是否合格类型
    private var flag: Boolean = false

    private var startDate = 1L
    private var endDate = 1L
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        serialChoice.items.addAll(SerialUtil.findSerial())
        serialChoice.setOnAction {
            SerialUtil.openSerial(serialChoice.value)
        }

        startDatePicker.value = LocalDate.now()
        endDatePicker.value = LocalDate.now()
//        if (!serialChoice.items.isEmpty()) {
//            serialChoice.value = serialChoice.items.first()
//        }

        typeExport();

    }

    @FXML
    fun outputAllExcel() {

        val serviceImpl = MemoryServiceImpl()
        val memoryArray = serviceImpl.selectMemoryAll()
        val data = memoryArray.toList()

        SaveFileUtil.saveFileToDirectory(data, "第一页", MemoryData::class.java, "全部")
    }


    private fun typeExport() {
        //单选排他
        radioButton1.toggleGroup = toggleGroup
        radioButton2.toggleGroup = toggleGroup

        toggleGroup.selectedToggleProperty().addListener { _, _, selectedToggle ->

            if (selectedToggle is RadioButton) {
                val selectedText = selectedToggle.text
                println("Selected option: $selectedText")
                flag = "合格品" == selectedText
            }
        }
    }

    fun outputTypeExcel() {
        if (flag) {

            val serviceImpl = MemoryServiceImpl()
            val data = serviceImpl.selectMemoryByQualified(flag).toList()
            SaveFileUtil.saveFileToDirectory(data, "第一页", MemoryData::class.java, "合格")

        } else {
            val serviceImpl = MemoryServiceImpl()
            serviceImpl.selectMemoryByQualified(false)
            val data = serviceImpl.selectMemoryByQualified(flag).toList()
            SaveFileUtil.saveFileToDirectory(data, "第一页", MemoryData::class.java, "不合格")

        }
    }

    fun startExportDate() {
        startDate = startDatePicker.value.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    fun endExportDate() {
        endDate = endDatePicker.value.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    fun outputDateExcel() {
        val serviceImpl = MemoryServiceImpl()
        val data = serviceImpl.selectMemoryByCheckTime(startDate, endDate).toList()
        SaveFileUtil.saveFileToDirectory(data, "第一页", MemoryData::class.java, "")
    }


}