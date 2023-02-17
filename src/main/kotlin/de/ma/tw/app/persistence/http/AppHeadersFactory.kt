package de.ma.tw.app.persistence.http

import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory
import io.smallrye.mutiny.Uni
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.MultivaluedMap

class AppHeadersFactory : ReactiveClientHeadersFactory() {

    override fun getHeaders(
        incomingHeaders: MultivaluedMap<String, String>?,
        clientOutgoingHeaders: MultivaluedMap<String, String>?
    ): Uni<MultivaluedMap<String, String>> {
        return Uni.createFrom().item {
            val newHeaders = MultivaluedHashMap<String, String>()
            newHeaders["igmobiledevice"] = listOf("Android")
            newHeaders["x-ig-os-name"] = listOf("android")
            newHeaders["x-ig-manufacturer"] = listOf("Google")
            newHeaders["x-ig-forms"] = listOf("sdk_gphone_x86")
            newHeaders["x-ig-os-version"] = listOf("11")
            newHeaders["x-ig-client-version"] = listOf("3.07.3")
            newHeaders["User-Agent"] = listOf("Tribal Wars Android 3.07.3")
            newHeaders["Accept-Encoding"] = listOf("gzip")
            newHeaders
        }
    }


}