package xyz.starchen.wincc.util

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder

object MyBatisUtil {
    private lateinit var factory: SqlSessionFactory

    init {
        try {
            val inputStream = Resources.getResourceAsStream("mybatis-config.xml")
            this.factory = SqlSessionFactoryBuilder().build(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSqlSession(): SqlSession {
        return factory.openSession(true)
    }
}
