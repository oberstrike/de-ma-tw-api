package de.ma.tw.utils.http

import com.github.tomakehurst.wiremock.WireMockServer
import com.marcinziolo.kotlin.wiremock.contains
import com.marcinziolo.kotlin.wiremock.equalTo
import com.marcinziolo.kotlin.wiremock.get
import com.marcinziolo.kotlin.wiremock.returns

class GameWireMockServer(sigOonJsonName: String = "signon.json",
                         port: Int = 8888) : WireMockServer(port) {

    override fun start() {
        super.start()

        val token = ""

        get {
            applyAppHeaders()
            queryParams contains "token" equalTo token
            urlPath equalTo "/login.php"
        }.returns {
            body = ""

        }
    }

}