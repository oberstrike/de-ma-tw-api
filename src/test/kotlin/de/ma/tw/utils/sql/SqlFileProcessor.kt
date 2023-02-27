package de.ma.tw.utils.sql

import io.vertx.pgclient.PgPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.sql.SQLException
import java.util.logging.Logger
import javax.enterprise.inject.spi.CDI

object SqlFileProcessor {
    private val COMMENT_PATTERN = Regex("--.*$")

    private val logger = Logger.getLogger(SqlFileProcessor::class.simpleName)

    suspend fun processTargetFile(targetFile: String) = withContext(Dispatchers.IO) {
        logger.info("Processing file: $targetFile")

        val pgPool = getPgPool()
        val lines = this::class.java.getResource(targetFile)?.openStream()?.bufferedReader()?.readLines()
            ?: throw FileNotFoundException("File not found: $targetFile")
        var nextCommand = ""
        var isDo = false

        for (line in lines) {
            val cleanedLine = COMMENT_PATTERN.replace(line, "").trim()
            if (cleanedLine.isEmpty()) continue
            nextCommand += cleanedLine
            if (isDo && !cleanedLine.startsWith("END \$\$;")) continue
            if (cleanedLine.startsWith("DO")) {
                isDo = true
                continue
            }
            if (cleanedLine.endsWith(";") || cleanedLine.startsWith("END \$\$;")) {
                try {
                    pgPool.query(nextCommand).execute().result()
                } catch (e: Exception) {
                    throw SQLException("Error executing SQL command: $nextCommand", e)
                }
                isDo = false
                nextCommand = ""
            }
        }
        logger.info("Processed file: $targetFile")
    }

    private fun getPgPool(): PgPool {
        try {
            return CDI.current().select(PgPool::class.java).get()
        } catch (e: Exception) {
            throw RuntimeException("Could not get the PgPool.", e)
        }
    }


}