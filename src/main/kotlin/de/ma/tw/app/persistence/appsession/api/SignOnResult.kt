package de.ma.tw.app.persistence.appsession.api

import de.ma.tw.core.domain.appsession.api.*
import de.ma.tw.core.domain.appsession.api.signon.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SignOnResult(
    val result: SignOnResponseImpl
)

@Serializable
data class SignOnResponseImpl(
    @SerialName("master_session")
    override val masterSession: MasterSessionImpl,
    override val type: String,
    override val fields: List<String>,
    override val status: Int,
    @SerialName("world_session")
    override val worldSession: WorldSessionImpl?,
    @SerialName("player_id")
    override val playerId: Int
) : SignOnResponse

@Serializable
data class MasterSessionImpl(
    override val name: String,
    @SerialName("player_id")
    override val playerId: Int,
    override val guest: Boolean,
    override val token: String,
    override val worlds: WorldsImpl,
    override val locale: String
) : MasterSession


@Serializable
data class WorldSessionImpl(
    override val token: String?,
    @SerialName("server_name")
    override val serverName: String?
) : WorldSession

@Serializable
data class WorldsImpl(
    override val other: List<WorldImpl>,
    override val active: List<WorldImpl>,
    override val prereg: List<WorldImpl>,
    override val suggested: List<WorldImpl>
) : Worlds

@Serializable
data class WorldImpl(
    override val login: Boolean,
    override val rank: String,
    override val register: Boolean,
    override val best: Boolean,
    @SerialName("server_name")
    override val serverName: String,
    override val url: String,
    override val active: String,
    override val name: String
) : World