package xyz.starchen.wincc.mapper

import org.apache.ibatis.annotations.Param
import xyz.starchen.wincc.pojo.MemoryData

interface MemoryMapper {
    fun selectMemoryAll(): Array<MemoryData>

    fun selectMemoryById(id: Int): MemoryData

    fun selectMemoryByQualified(qualified: Boolean): Array<MemoryData>

    fun selectMemoryByCheckTime(@Param("startTime") startTime: Int, @Param("endTime") endTime: Int): Array<MemoryData>

    fun insertMemory(memoryData: MemoryData): Int

    fun insertMemoryAll(memoryData: Array<MemoryData>): Int

    fun updateMemory(memoryData: MemoryData): Int

    fun deleteMemoryById(id: Int): Int
}