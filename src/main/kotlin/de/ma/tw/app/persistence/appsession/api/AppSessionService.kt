package de.ma.tw.app.persistence.appsession.api

import de.ma.tw.app.persistence.http.AppHeadersFactory
import kotlinx.serialization.json.JsonArray
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/m/m")
@RegisterClientHeaders(AppHeadersFactory::class)
interface AppSessionService {

    @POST
    @Path("/signon")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun signon(form: JsonArray, @QueryParam("hash") hash: String)
            : SignOnResult

}