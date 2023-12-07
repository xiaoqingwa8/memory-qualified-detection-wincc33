package xyz.starchen.wincc.util

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import xyz.starchen.wincc.pojo.MemoryData
import xyz.starchen.wincc.pojo.RunState
import xyz.starchen.wincc.service.impl.MemoryServiceImpl
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


object SerialUtil {
    private var serialPort: SerialPort? = null

    /**
     * 获取当前设备串口列表
     */
    fun findSerial(): Array<String> {
        val serialPorts = SerialPort.getCommPorts()//查找所有串口

        val list = ArrayList<String>()
        for (port in serialPorts) {
            list.add(port.systemPortName)
        }

        return list.toTypedArray()
    }

    /**
     * 设置并打开串口,并为串口添加监听器
     */
    fun openSerial(serialPortName: String): Boolean {
        val serialPort = SerialPort.getCommPort(serialPortName)

        // 设置波特率为112500
        serialPort.baudRate = 112500
        // 设置两位停止位
        serialPort.numStopBits = SerialPort.TWO_STOP_BITS
        // 设置超时
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING or SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000)

        if (serialPort.openPort()) {
            this.serialPort = serialPort

            // 添加数据接收监听器
            addListener()

            return true
        }

        return false
    }

    /**
     * 关闭串口
     */
    fun closeSerial() {
        if (serialPort == null) {
            return
        }

        this.serialPort!!.removeDataListener()
        this.serialPort!!.closePort()
    }

    /**
     * 使用串口发送 Byte 数据
     * @param data 要发送的数据
     */
    fun sendData(data: ByteArray): Boolean {
        if (this.serialPort == null) {
            return false
        }

        if (this.serialPort!!.writeBytes(data, data.size) == data.size) {
            return true
        }

        return false
    }

    private fun intToBytes(value: Int): ByteArray {
        val src = ByteArray(4)
        src[0] = (value shr 24 and 0xFF).toByte()
        src[1] = (value shr 16 and 0xFF).toByte()
        src[2] = (value shr 8 and 0xFF).toByte()
        src[3] = (value and 0xFF).toByte()
        return src
    }

    /**
     * 解析 Byte 数据并保存
     * @param data 要解析的 ByteArray
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun parseData(data: UByteArray) {
        // 防止第一次发送的一个乱码, 加一个判断
        if (data.size < 2) {
            return
        }

        when (data[0]) {
            // 收到的是运行状态
            0x01.toUByte() -> {
                parseRunStateAndStone(data[1])
            }
            // 收到的是检测数据
            0x02.toUByte() -> {
                parseDetectionDataAndStone(data)
            }
        }
    }

    private fun parseRunStateAndStone(data: UByte) {
        when (data) {
            0x00.toUByte() -> {
                SettingUtil.runState = RunState.RUN
            }
            0x01.toUByte() -> {
                SettingUtil.runState = RunState.STOP
            }
            0x02.toUByte() -> {
                SettingUtil.runState = RunState.BUSY
            }
            else -> {
                SettingUtil.runState = RunState.ERROR
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    private fun parseDetectionDataAndStone(data: UByteArray) {
        val memoryService = MemoryServiceImpl()
        // 第 2 个 Byte 为后面跟着的数据的数量
        val dataNum = data[1].toInt()

        // 获取传来的数据
        val differenceArray = IntArray(dataNum)
        for (i in 0 ..< dataNum) {
            // 因为一根内存的数据要占 2 Byte, 所以乘 2
            val ptr = 2 + (i * 2)

            val difference = data[ptr].toUInt() shl 8 or data[ptr + 1].toUInt()
            differenceArray[i] = difference.toInt()
        }

        // 判断最后一条数据与新数据是否为同一天
        val lastMemoryData = memoryService.selectLastData()
        val nowDate = Date()
        val isSameDay = DateUtil.isSameDay(Date(lastMemoryData.checkTime), Date())
        for (i in differenceArray.indices) {
            val qualified = differenceArray[i] < SettingUtil.maximumDifference

            // 如果是同一天, 就作为下一轮保存, 不是同一天, 则作为新一轮保存
            if (isSameDay) {
                memoryService.insertMemory(MemoryData(null, differenceArray[i], qualified, i + 1, lastMemoryData.round + 1, nowDate.time))
            } else {
                memoryService.insertMemory(MemoryData(null, differenceArray[i], qualified, i + 1, 1, nowDate.time))
            }
        }
    }

    /**
     * 监听器, 监听来自下位机的数据, 并将其保存
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    private fun addListener() {
        this.serialPort!!.addDataListener(object :SerialPortDataListener {
            override fun getListeningEvents(): Int {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
            }

            override fun serialEvent(event: SerialPortEvent) {
                if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return
                }

                // 接收来自下位机的数据
                val data = ArrayList<UByte>()
                while (serialPort!!.bytesAvailable() != 0) {
                    val newData = ByteArray(serialPort!!.bytesAvailable())
                    serialPort!!.readBytes(newData, newData.size)
                    data.addAll(newData.toUByteArray())
                    TimeUnit.MILLISECONDS.sleep(20)
                }

                parseData(data.toUByteArray())
            }
        })
    }
}