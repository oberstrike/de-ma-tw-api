package de.ma.tw.utils.http

import com.github.tomakehurst.wiremock.WireMockServer
import com.marcinziolo.kotlin.wiremock.*
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import java.io.FileNotFoundException
import java.util.*
import java.util.logging.Logger


class WireMockExtensions : QuarkusTestResourceLifecycleManager {

    companion object {
        val logger: Logger = Logger.getLogger(WireMockExtensions::class.simpleName)

        lateinit var appWireMockServer: AppWireMockServer

        lateinit var gameWireMockServer: GameWireMockServer

    }


    override fun start(): MutableMap<String, String> {
        initAppWireMockServer()
        initGameWireMockServer()

        return Collections.singletonMap(
            "tribalwars.base-url",
            appWireMockServer.baseUrl()
        )
    }

    private fun initGameWireMockServer() {
        gameWireMockServer = GameWireMockServer(
            port = 8889
        )
        logger.info("Starting gameWireMockServer")
        gameWireMockServer.start()
    }

    private fun initAppWireMockServer() {
        appWireMockServer = AppWireMockServer(
            port = 8890
        )

        logger.info("Starting appWireMockServer")
        appWireMockServer.start()

        logger.info("AppWireMockServerUrl: ${appWireMockServer.baseUrl()}")

    }

    override fun stop() {
        appWireMockServer.stop()
        gameWireMockServer.stop()
    }


}