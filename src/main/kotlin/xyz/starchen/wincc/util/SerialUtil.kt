package xyz.starchen.wincc.util

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import java.util.concurrent.TimeUnit


object SerialUtil {
    private var serialPort: SerialPort? = null

    fun findSerial(): Array<String> {
        val serialPorts = SerialPort.getCommPorts()//查找所有串口

        val list = ArrayList<String>()
        for (port in serialPorts) {
            list.add(port.systemPortName)
        }

        return list.toTypedArray()
    }

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

    fun closeSerial() {
        if (serialPort == null) {
            return
        }

        this.serialPort!!.removeDataListener()
        this.serialPort!!.closePort()
    }

    fun sendData(data: ByteArray): Boolean {
        if (this.serialPort == null) {
            return false
        }

        if (this.serialPort!!.writeBytes(data, data.size) == data.size) {
            return true
        }

        return false
    }

    private fun parseData(receivedData: ByteArray) {

    }

    private fun addListener() {
        this.serialPort!!.addDataListener(object :SerialPortDataListener {
            override fun getListeningEvents(): Int {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
            }

            override fun serialEvent(event: SerialPortEvent) {
                if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return
                }

                var data = ""
                while (serialPort!!.bytesAvailable() != 0) {
                    val newData = ByteArray(serialPort!!.bytesAvailable())
                    serialPort!!.readBytes(newData, newData.size)
                    data += String(newData)
                    TimeUnit.MILLISECONDS.sleep(20)
                }

                println(data)
            }
        })
    }
}