package xyz.starchen.wincc.util

import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val sf = SimpleDateFormat("yyyy-MM-dd")
        val day1 = sf.format(date1)
        val day2 = sf.format(date2)
        return day1 == day2
    }
}