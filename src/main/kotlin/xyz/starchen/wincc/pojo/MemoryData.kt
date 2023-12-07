package xyz.starchen.wincc.pojo

import com.alibaba.excel.annotation.ExcelProperty

data class MemoryData(
    @ExcelProperty("编号")
    val id: Int?,
    @ExcelProperty("曲率")
    val difference: Int,
    @ExcelProperty("是否合格")
    val qualified: Boolean,
    @ExcelProperty("批次号")
    val number: Int,
    @ExcelProperty("轮次")
    val round: Int,
    @ExcelProperty("检查日期")
    val checkTime: Long
) {
    override fun toString(): String {
        return "MemoryData(id=$id, difference=$difference, qualified=$qualified, number=$number, round=$round, checkTime=$checkTime)"
    }
}
