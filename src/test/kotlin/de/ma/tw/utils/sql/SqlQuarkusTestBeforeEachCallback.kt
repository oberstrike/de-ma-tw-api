package de.ma.tw.utils.sql

import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.utils.sql.SqlFileProcessor.processTargetFile
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.reflect.full.createInstance

class SqlQuarkusTestBeforeEachCallback : QuarkusTestBeforeEachCallback {

    private lateinit var packageName: String


    override fun beforeEach(context: QuarkusTestMethodContext) = runBlocking(
        Dispatchers.IO
    ) {
        packageName = "/${
            context.testInstance::class.qualifiedName!!
                .substringBeforeLast(".")
                .replace('.', '/')
        }"

        sqlHandler(context)
        testScenarioHandler(context)
    }

    private suspend fun sqlHandler(context: QuarkusTestMethodContext) {
        context.testMethod.getAnnotationsByType(Sql::class.java)
            .flatMap { it.before.toList() }
            .map { targetFile -> "$packageName/$targetFile" }
            .forEach { targetFile -> processTargetFile(targetFile) }

    }


    private suspend fun testScenarioHandler(context: QuarkusTestMethodContext) {
        val testScenarios = context.testMethod.getAnnotationsByType(TestScenario::class.java)
        if(testScenarios.isEmpty()){
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