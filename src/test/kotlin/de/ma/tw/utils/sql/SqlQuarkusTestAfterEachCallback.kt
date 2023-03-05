package de.ma.tw.utils.sql

import de.ma.tw.app.web.utils.TestScenario
import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestAfterTestExecutionCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import io.vertx.pgclient.PgPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.logging.Logger
import javax.enterprise.inject.spi.CDI

class SqlQuarkusTestAfterEachCallback : QuarkusTestAfterTestExecutionCallback {


    override fun afterTestExecution(context: QuarkusTestMethodContext) {

        //get the package of the context.testInstance
        var packageName = context.testInstance::class.qualifiedName!!.substringBeforeLast('.')

        //replace the '.' in packageName with '/' and add the prefix 'sql/'
        packageName = "/${packageName.replace('.', '/')}"

        val sqlAnnotations = context.testMethod.getAnnotationsByType(Sql::class.java)
        val testScenarios = context.testMethod.getAnnotationsByType(TestScenario::class.java)

        if (sqlAnnotations.isNotEmpty()) {
            for (sqlAnnotation in sqlAnnotations) {
                val targetFiles = sqlAnnotation.after
                for (targetFile in targetFiles) {
                    logger.info("Proccessing file: $targetFile in After Each.")
                    //put the package name add the target file name
                    SqlFileProcessor.processTargetFile("$packageName/$targetFile")
                }
            }
        }

    }

    companion object {

        private val logger = LoggerFactory.getLogger(SqlQuarkusTestAfterEachCallback::class.java)

    }

}