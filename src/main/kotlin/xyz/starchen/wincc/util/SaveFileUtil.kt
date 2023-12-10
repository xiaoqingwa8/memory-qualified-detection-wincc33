package xyz.starchen.wincc.util

import com.alibaba.excel.EasyExcel
import javafx.stage.DirectoryChooser
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object SaveFileUtil {
    fun<T> saveFileToDirectory(data: Collection<T>,sheetName:String,head: Class<T>,fileName:String) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Choose Directory"

        val selectedDirectory = directoryChooser.showDialog(null)
        if (selectedDirectory != null) {
            val date = Date()
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
            val currentDateStr = currentDate.format(date)
            val filePath = File(selectedDirectory, currentDateStr+fileName+".xlsx").toPath().toString()
            try {
                EasyExcel.write(filePath, head).sheet(sheetName).doWrite(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}