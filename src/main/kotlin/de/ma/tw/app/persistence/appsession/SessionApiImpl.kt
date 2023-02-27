package de.ma.tw.app.persistence.appsession

import de.ma.tw.app.persistence.appsession.api.AppSessionService
import de.ma.tw.app.persistence.http.AppHeadersFactory
import de.ma.tw.app.persistence.http.AppHttpClientOptions
import de.ma.tw.app.persistence.http.request.RequestUtils
import de.ma.tw.core.domain.appsession.api.AppSessionApi
import de.ma.tw.core.domain.appsession.api.signon.SignOnForm
import de.ma.tw.core.domain.appsession.api.signon.SignOnResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.RestClientBuilder
import java.net.URI
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class AppSessionApiService(
    @ConfigProperty(name = "tribalwars.base-url")
    private val url: String,
) : AppSessionApi {

    private val sessionApi: AppSessionService = RestClientBuilder.newBuilder()
        .baseUri(URI.create(url))
        .register(AppHttpClientOptions::class.java)
        .register(AppHeadersFactory::class.java)
        .build(AppSessionService::class.java)


    override suspend fun signOn(signOnForm: SignOnForm): SignOnResponse {
        val encodeToJsonElement = Json.encodeToJsonElement(
            SignOnFormImpl(
                intent = signOnForm.intent,
                username = signOnForm.username,
                password = signOnForm.password
            )
        )
        val primitive = JsonPrimitive("password")

        val request = RequestUtils.createRequest(
            listOf(primitive, encodeToJsonElement)
        )

        val signOnResult = sessionApi.signon(
            request.form,
            request.hash
        )

        return signOnResult.result
    }

}


