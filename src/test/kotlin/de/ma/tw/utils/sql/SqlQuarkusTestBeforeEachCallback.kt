package de.ma.tw.utils.sql

import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.utils.sql.SqlFileProcessor.processTargetFile
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestBeforeTestExecutionCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import javax.enterprise.inject.spi.CDI
import kotlin.reflect.full.createInstance

class SqlQuarkusTestBeforeEachCallback : QuarkusTestBeforeTestExecutionCallback {

    private lateinit var packageName: String

    companion object {

        private val logger = LoggerFactory.getLogger(SqlQuarkusTestBeforeEachCallback::class.java)

    }


    override fun beforeTestExecution(context: QuarkusTestMethodContext) {
        packageName = "/${
            context.testInstance::class.qualifiedName!!
                .substringBeforeLast(".")
                .replace('.', '/')
        }"

        sqlHandler(context)
        testScenarioHandler(context)
    }


    private fun sqlHandler(context: QuarkusTestMethodContext) {
        context.testMethod.getAnnotationsByType(Sql::class.java)
            .flatMap { it.before.toList() }
            .map { targetFile -> "$packageName/$targetFile" }
            .forEach { targetFile -> processTargetFile(targetFile) }

    }


    private fun testScenarioHandler(context: QuarkusTestMethodContext) {
        val testScenarios = context.testMethod.getAnnotationsByType(TestScenario::class.java)
        if (testScenarios.isEmpty()) {
            return
        }

        if (testScenarios.size > 1) {
            throw IllegalArgumentException("Expected exactly one TestScenario annotation on the test method, but found ${testScenarios.size}.")
        }


        val testScenario = testScenarios.first()

        val scenario = testScenario.scenario.createInstance()

        scenario.before()
            .map { targetFile -> "$packageName/$targetFile" }
            .forEach { targetFile -> processTargetFile(targetFile) }

    }


}