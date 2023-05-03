package com.example.kopring.test

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.test.jdbc.JdbcTestUtils
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import java.sql.DatabaseMetaData
import java.sql.SQLException


class IntegrationTestExecuteListener : AbstractTestExecutionListener() {

    override fun beforeTestMethod(testContext: TestContext) {
        val jdbcTemplate = getJdbcTemplate(testContext)
        val transactionTemplate = getTransactionTemplate(testContext)

        truncateAllTables(jdbcTemplate, transactionTemplate)
    }

    private fun getTransactionTemplate(testContext: TestContext): TransactionTemplate {
        return testContext.applicationContext.getBean(TransactionTemplate::class.java)
    }

    private fun getJdbcTemplate(testContext: TestContext): JdbcTemplate {
        return testContext.applicationContext.getBean(JdbcTemplate::class.java)
    }

    private fun truncateAllTables(jdbcTemplate: JdbcTemplate, transactionTemplate: TransactionTemplate) {
        transactionTemplate.execute(object : TransactionCallbackWithoutResult() {
            override fun doInTransactionWithoutResult(status: TransactionStatus) {
                jdbcTemplate.execute("set FOREIGN_KEY_CHECKS = 0;")
                JdbcTestUtils.deleteFromTables(jdbcTemplate, *getAllTables(jdbcTemplate).toTypedArray())
                jdbcTemplate.execute("set FOREIGN_KEY_CHECKS = 1;")
            }
        })
    }

    private fun getAllTables(jdbcTemplate: JdbcTemplate): List<String> {
        try {
            jdbcTemplate.dataSource?.connection.use { connection ->
                val metaData: DatabaseMetaData = connection!!.metaData
                val tables: MutableList<String> = ArrayList()
                metaData.getTables(null, null, null, arrayOf("TABLE")).use { resultSet ->
                    while (resultSet.next()) {
                        tables.add(resultSet.getString("TABLE_NAME"))
                    }
                }
                tables.remove("flyway_schema_history")
                return tables
            }
        } catch (exception: SQLException) {
            throw IllegalStateException(exception)
        }
    }
}
