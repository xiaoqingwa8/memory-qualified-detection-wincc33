package xyz.starchen.wincc.util

import xyz.starchen.wincc.pojo.RunState
import java.util.*

object SettingUtil {
    private val properties = Properties()

    var runState = RunState.NOT_LINKED
    var maximumDifference = 1500

    fun loadSetting(configFilePath: String) {
        val settingFile = javaClass.getResourceAsStream("/setting.properties")
        // 解析配置文件
        this.properties.load(settingFile)
    }
}