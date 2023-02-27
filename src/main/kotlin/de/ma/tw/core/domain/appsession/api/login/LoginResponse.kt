package de.ma.tw.core.domain.appsession.api.login

import de.ma.tw.core.domain.appsession.api.signon.World
import kotlinx.serialization.SerialName

interface LoginResponse {
    val sid: String

    @SerialName("login_url")
    val loginUrl: String

    val world: World

    val sat: Boolean

    @SerialName("sitter_id")
    val sitterId: Boolean?

    @SerialName("sitter_name")
    val sitterName: String

    val sleep: Boolean

    val ban: Boolean

    @SerialName("node_server")
    val nodeServer: String

    @SerialName("active_event")
    val activeEvent: String?

    val locale: String
}