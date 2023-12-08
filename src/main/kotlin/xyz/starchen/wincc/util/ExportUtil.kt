package xyz.starchen.wincc.util

import com.alibaba.excel.EasyExcel

object ExportUtil {
    fun<T> exportExcel(data: Collection<T>,sheetName:String,head: Class<T>){
        //保存excel文件路径
        val fileName: String = "src/main/kotlin/xyz/starchen/wincc/excel/" + System.currentTimeMillis() + ".xlsx"

        EasyExcel.write(fileName, head)
            .sheet(sheetName)
            .doWrite(data)
    }
}