package de.ma.tw.utils.http

import com.github.tomakehurst.wiremock.WireMockServer
import com.marcinziolo.kotlin.wiremock.*
import de.ma.tw.app.persistence.appsession.api.SignOnResponseImpl
import de.ma.tw.app.persistence.appsession.api.SignOnResult
import de.ma.tw.core.domain.appsession.api.signon.SignOnResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString


class AppWireMockServer(sigOonJsonName: String = "signon.json",
                        port: Int = 8888) : WireMockServer(port) {
    override fun start() {
        super.start()

        initSignOn()
    }

    lateinit var signOnResponse: SignOnResult

    private val signOnInputJson = this::class.java.getResourceAsStream(sigOonJsonName)
        ?.bufferedReader()?.readText()



    private fun initSignOn() {
        if (signOnInputJson == null) {
            throw IllegalArgumentException("The file signOn is not given.")
        }

        signOnResponse = Json.decodeFromString<SignOnResult>(signOnInputJson)

        post {
            applyAppHeaders()
            queryParams contains "hash" equalTo "84086786c17606f0bafaed06a35b9a6184221e72"
            urlPath equalTo "/m/m/signon"
            body equalTo "[\"password\",{\"intent\":\"login\",\"username\":\"test\",\"password\":\"test\"}]"
        }.returnsJson {
            statusCode = 200
            header = "Content-Type" to "application/json"
            body = signOnInputJson
        }
    }


}


fun RequestSpecification.applyAppHeaders() {
    headers contains "igmobiledevice" equalTo "Android"
    headers contains "x-ig-os-name" equalTo "android"
    headers contains "x-ig-manufacturer" equalTo "Google"
    headers contains "x-ig-forms" equalTo "sdk_gphone_x86"
    headers contains "x-ig-os-version" equalTo "11"
    headers contains "x-ig-client-version" equalTo "3.07.3"
    headers contains "User-Agent" equalTo "Tribal Wars Android 3.07.3"
    headers contains "Content-Type" equalTo "application/json"
    headers contains "Accept-Encoding" equalTo "gzip"
}
