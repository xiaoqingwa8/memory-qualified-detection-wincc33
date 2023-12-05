package xyz.starchen.wincc.util

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent

object SerialUtil {
    private var serialPort: SerialPort? = null

    fun findSerial(): Array<SerialPort> {
        val serialPorts = SerialPort.getCommPorts()//查找所有串口
        for (port in serialPorts) {
            println("Port:" + port.systemPortName)//打印串口名称，如COM4
            println("PortDesc:" + port.portDescription)//打印串口类型，如USB Serial
            println("PortDesc:" + port.descriptivePortName)//打印串口的完整类型，如USB-SERIAL CH340(COM4)
        }
        return serialPorts
    }

    fun openSerial(serialPort: SerialPort): Boolean {
        // 设置波特率为112500
        serialPort.baudRate = 112500
        // 设置两位停止位
        serialPort.numStopBits = SerialPort.TWO_STOP_BITS
        // 设置超时
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING or SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000)

        if (serialPort.openPort()) {
            this.serialPort = serialPort
            return true
        }

        return false
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

    fun receiveData(): ByteArray {
        if (this.serialPort == null) {
            return byteArrayOf()
        }

        val readData: StringBuilder = StringBuilder()
        while (this.serialPort!!.bytesAvailable() > 0) {
            val newData = ByteArray(this.serialPort!!.bytesAvailable())
            this.serialPort!!.readBytes(newData, newData.size)
            readData.append(String(newData))
            Thread.sleep(100)
        }

        return readData.toString().toByteArray()
    }

    fun addListener() {
        if (this.serialPort == null) {
            return
        }

        this.serialPort!!.addDataListener(object :SerialPortDataListener {
            override fun getListeningEvents(): Int {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
            }

            override fun serialEvent(event: SerialPortEvent?) {
                TODO("这里要触发数据显示与写入")
            }
        })
    }
}