package de.ma.tw.utils.sql

import de.ma.tw.app.web.utils.IScenario
import de.ma.tw.app.web.utils.TestScenario
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.mutiny.pgclient.PgPool
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import javax.inject.Inject
import kotlin.math.log

abstract class AbstractGatewayTest {

    companion object {
        private val logger = LoggerFactory.getLogger(AbstractGatewayTest::class.java)

        private const val CLEAR_DATABASE_SCRIPT = "" +
                "DELETE FROM appsessionentity;\n" +
                "DELETE FROM accountentity;\n";
    }


    @Inject
    lateinit var pgPool: PgPool

    fun <T : IScenario> runLocalTest(scenario: T, block: suspend (T) -> Unit){
        val beforeFiles = scenario.before().map {
            "/${this::class.java.packageName.replace(".", "/")}/$it"
        }

        runBlocking {
            clearDatabase()
            beforeFiles.forEach { SqlFileProcessor.processTargetFile(it) }
            block.invoke(scenario)
            clearDatabase()
        }
    }

    fun runLocalTest(block: suspend () -> Unit) {
        runBlocking {
            logger.info("Clear the database at the start.")
            clearDatabase()
            block.invoke()
            logger.info("Clear the database after the test.")
            clearDatabase()
        }
    }

    private suspend fun clearDatabase() {
        logger.info("Starting to clear the database...")
        try{
            val rowSet = pgPool.query(CLEAR_DATABASE_SCRIPT).execute().awaitSuspending()
            if (rowSet != null) {
                logger.info("There was a Result: ${rowSet.map { it.toString() }}")
            }
        }catch (e: Exception){
            logger.error("It was not possible to clear the database.")
            throw e;
        }


        logger.info("Cleared the database.")
    }

}