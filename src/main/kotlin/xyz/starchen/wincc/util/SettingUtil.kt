package xyz.starchen.wincc.util

import java.util.*

object SettingUtil {
    private val properties = Properties()

    fun loadSetting(configFilePath: String) {
        val settingFile = javaClass.getResourceAsStream("/setting.properties")
        // 解析配置文件
        this.properties.load(settingFile)
    }
}