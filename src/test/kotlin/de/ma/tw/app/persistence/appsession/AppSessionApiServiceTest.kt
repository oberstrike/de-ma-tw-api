package de.ma.tw.app.persistence.appsession

import de.ma.tw.utils.http.WireMockExtensions
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(WireMockExtensions::class)
class AppSessionApiServiceTest {

    @Inject
    lateinit var sessionApiService: AppSessionApiService


    @Test
    fun test() = runBlocking{
        val response = sessionApiService.signOn(
            SignOnFormImpl("login", "test", "test")
        )

        println(response)

    }
}