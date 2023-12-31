package xyz.starchen.wincc.mapper.impl

import xyz.starchen.wincc.mapper.MemoryMapper
import xyz.starchen.wincc.pojo.MemoryData
import xyz.starchen.wincc.util.MyBatisUtil
import java.util.*


class MemoryMapperImpl : MemoryMapper {
    override fun selectMemoryAll(): Array<MemoryData> {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectMemoryAll()
        }
    }

    override fun selectMemoryById(id: Int): MemoryData {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectMemoryById(id)
        }
    }

    override fun selectMemoryByQualified(qualified: Boolean): Array<MemoryData> {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectMemoryByQualified(qualified)
        }
    }

    override fun selectMemoryByCheckTime(startTime: Long, endTime: Long): Array<MemoryData> {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectMemoryByCheckTime(startTime, endTime)
        }
    }



    override fun selectLastData(): MemoryData {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectLastData()
        }
    }

    override fun insertMemory(memoryData: MemoryData): Int {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.insertMemory(memoryData)
        }
    }

    override fun insertMemoryAll(memoryData: Array<MemoryData>): Int {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.insertMemoryAll(memoryData)
        }
    }

    override fun updateMemory(memoryData: MemoryData): Int {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.updateMemory(memoryData)
        }
    }

    override fun deleteMemoryById(id: Int): Int {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.deleteMemoryById(id)
        }
    }

    override fun selectMemoryBySpecificCheckTime(checkTime: Date): Array<MemoryData> {
        MyBatisUtil.getSqlSession().use { session ->
            val mapper: MemoryMapper = session.getMapper(MemoryMapper::class.java)
            return mapper.selectMemoryBySpecificCheckTime(checkTime)
        }
    }
}