package de.ma.tw.app.web.apis

import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import de.ma.tw.core.api.appsession.GetAppSessionUseCase
import de.ma.tw.core.domain.appsession.model.AppSession
import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/session")
class AppSessionResource(
) {




}