package de.ma.tw.app.persistence.http

import io.vertx.core.http.HttpClientOptions
import javax.ws.rs.ext.ContextResolver


class AppHttpClientOptions : ContextResolver<HttpClientOptions> {

    override fun getContext(type: Class<*>?): HttpClientOptions {
        val options = HttpClientOptions()
        options.isTryUseCompression = true

        return options
    }

}