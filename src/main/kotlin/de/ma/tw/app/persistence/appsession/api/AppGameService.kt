package de.ma.tw.app.persistence.appsession.api

import de.ma.tw.app.persistence.http.AppHeadersFactory
import kotlinx.serialization.json.JsonArray
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@Path("/m/m")
@RegisterClientHeaders(AppHeadersFactory::class)
interface AppGameService {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun login(json: JsonArray, @QueryParam("hash") hash: String) : LoginResult


    @POST
    @Path("/village_data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getVillageData(form: JsonArray, @QueryParam("hash") hash: String)
            : VillageDataResult
}