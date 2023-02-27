package de.ma.tw.app.persistence.appsession.api

import de.ma.tw.core.domain.appsession.api.login.LoginResponse
import de.ma.tw.core.domain.appsession.api.signon.World
import kotlinx.serialization.SerialName

data class LoginResult(
    val result: LoginResponseImpl
)

data class LoginResponseImpl(
    override val ban: Boolean,
    override val locale: String,
    @SerialName("login_url")
    override val loginUrl: String,
    @SerialName("node_server")
    override val nodeServer: String,
    override val sat: Boolean,
    override val sid: String,
    @SerialName("sitter_id")
    override val sitterId: Boolean?,
    @SerialName("sitter_name")
    override val sitterName: String,
    override val sleep: Boolean,
    override val world: World,
    @SerialName("active_event")
    override val activeEvent: String?
) : LoginResponse