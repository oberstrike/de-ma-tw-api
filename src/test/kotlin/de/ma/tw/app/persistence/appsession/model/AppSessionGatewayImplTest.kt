package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.app.web.utils.IScenario
import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.utils.database.DatabaseTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(DatabaseTestResource::class)
class AppSessionGatewayImplTest {

    @Inject
    private lateinit var appSessionGatewayImpl: AppSessionGatewayImpl


    class OneAppSessionScenario: IScenario{
        override fun before(): List<String> {
            return listOf("one_app_session_scenario.sql")
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun persistTest() = runTest {

    }

    @ParameterizedTest
    @TestScenario(OneAppSessionScenario::class)
    fun findByIdTest() {

    }
}