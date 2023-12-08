package xyz.starchen.wincc.util

import java.util.EventListener

interface SerialReceiveDataListener: EventListener {
    fun notice()
}