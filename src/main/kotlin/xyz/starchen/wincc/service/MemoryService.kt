package xyz.starchen.wincc.service

import org.apache.ibatis.annotations.Param
import xyz.starchen.wincc.pojo.MemoryData

interface MemoryService {
    fun selectMemoryAll(): Array<MemoryData>

    fun selectMemoryById(id: Int): MemoryData

    fun selectMemoryByQualified(qualified: Boolean): Array<MemoryData>

    fun selectMemoryByCheckTime(@Param("startTime") startTime: Long, @Param("endTime") endTime: Long): Array<MemoryData>

    fun selectLastData(): MemoryData

    fun insertMemory(memoryData: MemoryData): Int

    fun insertMemoryAll(memoryData: Array<MemoryData>): Int

    fun updateMemory(memoryData: MemoryData): Int

    fun deleteMemoryById(id: Int): Int
}