package de.ma.tw.utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.marcinziolo.kotlin.wiremock.*
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import java.io.FileNotFoundException
import java.util.*
import java.util.logging.Logger


class WireMockExtensions : QuarkusTestResourceLifecycleManager {

    companion object {
        val logger: Logger = Logger.getLogger(WireMockExtensions::class.simpleName)
    }

    private lateinit var wireMockServer: WireMockServer

    override fun start(): MutableMap<String, String> {
        wireMockServer = WireMockServer()
        wireMockServer.start()

        val signOnInputJson =
            this::class.java.getResourceAsStream("signon.json")
                ?.bufferedReader()?.readText()?: throw FileNotFoundException("Signon.json wasn't found")

        wireMockServer.post {
            applyAppHeaders()
            queryParams contains "hash" equalTo "84086786c17606f0bafaed06a35b9a6184221e72"
            urlPath equalTo "/m/m/signon"
            body equalTo "[\"password\",{\"intent\":\"login\",\"username\":\"test\",\"password\":\"test\"}]"
        }.returnsJson {
            statusCode = 200
            header = "Content-Type" to "application/json"
            body = signOnInputJson
        }

        logger.info("base-url: ${wireMockServer.baseUrl()}")

        return Collections.singletonMap(
            "tribalwars.base-url",
            wireMockServer.baseUrl()
        )
    }

    override fun stop() {
        wireMockServer.stop()
    }


}