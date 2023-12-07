package xyz.starchen.wincc.controller

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import xyz.starchen.wincc.pojo.MemoryData
import xyz.starchen.wincc.service.impl.MemoryServiceImpl
import xyz.starchen.wincc.util.ExportUtil.exportExcel
import xyz.starchen.wincc.util.SerialUtil
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class ToolsController : Initializable {
    @FXML
    private var serialChoice = ChoiceBox<String>()

    @FXML
    private val datePicker = DatePicker()

    @FXML
    private var radioButton1 = RadioButton()

    @FXML
    private var radioButton2 = RadioButton()

    @FXML
    private var toggleGroup = ToggleGroup()

    //获取所选日期
    private var date = Date()

    //判断选择是否合格类型
    private var flag: Boolean = false

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        serialChoice.items.addAll(SerialUtil.findSerial())
        serialChoice.setOnAction {
            SerialUtil.openSerial(serialChoice.value)
        }

//        if (!serialChoice.items.isEmpty()) {
//            serialChoice.value = serialChoice.items.first()
//        }
        datePicker.value = LocalDate.now()
        typeExport();

    }

    @FXML
    fun outputAllExcel() {
        val serviceImpl = MemoryServiceImpl()
        val memoryArray = serviceImpl.selectMemoryAll()
        val data = memoryArray.toList()

        exportExcel(data, "第一页", MemoryData::class.java)
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
            exportExcel(data, "第一页", MemoryData::class.java)
        } else {
            val serviceImpl = MemoryServiceImpl()
            serviceImpl.selectMemoryByQualified(false)
            val data = serviceImpl.selectMemoryByQualified(flag).toList()
            exportExcel(data, "第一页", MemoryData::class.java)
        }
    }

    fun exportByDate() {
        println("handleDateSelection called. datePicker: $datePicker, datePicker.value: ${datePicker.value}")

        if (datePicker.value != null) {
            val dateString = datePicker.value.toString()
            val format = SimpleDateFormat("yyyy-MM-dd")
            date = format.parse(dateString)
        }
    }

    fun outputDateExcel() {
        val serviceImpl = MemoryServiceImpl()
        val data = serviceImpl.selectMemoryBySpecificCheckTime(date).toList()
        exportExcel(data, "第一页", MemoryData::class.java)
    }


}