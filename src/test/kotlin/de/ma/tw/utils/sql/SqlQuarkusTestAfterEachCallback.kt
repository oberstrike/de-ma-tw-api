package de.ma.tw.utils.sql

import de.ma.tw.app.web.utils.TestScenario
import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import io.vertx.pgclient.PgPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.enterprise.inject.spi.CDI

class SqlQuarkusTestAfterEachCallback : QuarkusTestAfterEachCallback {


    override fun afterEach(context: QuarkusTestMethodContext) = runBlocking(
        Dispatchers.IO
    ) {
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
                    //put the package name add the target file name
                    SqlFileProcessor.processTargetFile("$packageName/$targetFile")
                }
            }
            //clear the database
            val pgPool = CDI.current().select(PgPool::class.java).get()
            pgPool.query(CLEAR_DATABASE_SCRIPT).execute().result()
        }


        if(testScenarios.isNotEmpty() || sqlAnnotations.isNotEmpty()){
            val pgPool = CDI.current().select(PgPool::class.java).get()
            pgPool.query(CLEAR_DATABASE_SCRIPT).execute().result()
        }

    }

    companion object {
        private const val CLEAR_DATABASE_SCRIPT = "DO \$\$ DECLARE\n" +
                "  r RECORD;\n" +
                "BEGIN\n" +
                "  -- Get all tables in the current schema\n" +
                "  FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP\n" +
                "    EXECUTE 'DELETE FROM ' || quote_ident(r.tablename) || ';';\n" +
                "  END LOOP;\n" +
                "END \$\$;"
    }

}