package de.ma.tw.utils.sql

import io.quarkus.test.junit.callback.QuarkusTestBeforeClassCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class SqlQuarkusTestBeforeClassCallback : QuarkusTestBeforeClassCallback {

    override fun beforeClass(testClass: Class<*>) = runBlocking(
        Dispatchers.IO
    ) {
        testClass.annotations.filterIsInstance(Sql::class.java)
            .flatMap { it.before.toList() }
            .forEach { SqlFileProcessor.processTargetFile(it) }
    }
}