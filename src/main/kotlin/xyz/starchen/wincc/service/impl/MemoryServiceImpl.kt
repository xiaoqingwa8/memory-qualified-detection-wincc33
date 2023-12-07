package xyz.starchen.wincc.service.impl

import xyz.starchen.wincc.mapper.impl.MemoryMapperImpl
import xyz.starchen.wincc.pojo.MemoryData
import xyz.starchen.wincc.service.MemoryService

class MemoryServiceImpl: MemoryService {
    private val mapper = MemoryMapperImpl()

    override fun selectMemoryAll(): Array<MemoryData> {
        return mapper.selectMemoryAll()
    }

    override fun selectMemoryById(id: Int): MemoryData {
        return mapper.selectMemoryById(id)
    }

    override fun selectMemoryByQualified(qualified: Boolean): Array<MemoryData> {
        return mapper.selectMemoryByQualified(qualified)
    }

    override fun selectMemoryByCheckTime(startTime: Long, endTime: Long): Array<MemoryData> {
        return mapper.selectMemoryByCheckTime(startTime, endTime)
    }

    override fun selectLastData(): MemoryData {
        return mapper.selectLastData()
    }

    override fun insertMemory(memoryData: MemoryData): Int {
        return mapper.insertMemory(memoryData)
    }

    override fun insertMemoryAll(memoryData: Array<MemoryData>): Int {
        return mapper.insertMemoryAll(memoryData)
    }

    override fun updateMemory(memoryData: MemoryData): Int {
        return mapper.updateMemory(memoryData)
    }

    override fun deleteMemoryById(id: Int): Int {
        return mapper.deleteMemoryById(id)
    }
}