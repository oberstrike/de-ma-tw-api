package de.ma.tw.app.web.infrastructure

import io.quarkus.runtime.StartupEvent
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.resource.ClassLoaderResourceAccessor
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class InitLiquibaseService(
    @ConfigProperty(name = "liquibase.url")
    val databaseUrl: String,
    @ConfigProperty(name = "quarkus.datasource.username")
    val username: String,
    @ConfigProperty(name = "quarkus.datasource.password")
    val password: String,
    @ConfigProperty(name = "quarkus.liquibase.change-log")
    val changeLogLocation: String
) {

    private val logger = LoggerFactory.getLogger(InitLiquibaseService::class.java)


    fun onStart(@Observes ev: StartupEvent) {
        liquibaseUpgrade()
    }

    private fun liquibaseUpgrade() {
        var liquibase: Liquibase? = null
        try {
            val resourceAccessor = ClassLoaderResourceAccessor(Thread.currentThread().contextClassLoader)
            val conn =
                DatabaseFactory.getInstance().openConnection(databaseUrl, username, password, null, resourceAccessor)
            liquibase = Liquibase(changeLogLocation, resourceAccessor, conn)
            liquibase.update(Contexts(), LabelExpression())
        } catch (e: Exception) {
            logger.error("Liquibase Migration Exception: " + e.message)
        } finally {
            liquibase?.close()
        }
    }


}