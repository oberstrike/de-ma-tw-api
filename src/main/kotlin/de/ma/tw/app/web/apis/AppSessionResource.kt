package de.ma.tw.app.web.apis

import de.ma.tw.core.api.session.CreateAppSessionUseCase
import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import kotlinx.serialization.Serializable
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/session")
class AppSessionResource(
    private val createAppSessionUseCase: CreateAppSessionUseCase
) {

    @Serializable
    data class AppSessionCreateParamsImpl(
        override val username: String,
        override val password: String
    ) : AppSessionCreateParams {

    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun create(
        appSessionCreateParams: AppSessionCreateParamsImpl
    ): AppSessionCreationResult {
        return createAppSessionUseCase(appSessionCreateParams)
    }
}