package de.ma.tw.core.domain.appsession.api.signon

import de.ma.tw.app.persistence.appsession.api.WorldSessionImpl

interface SignOnResponse {
    val status: Int
    val type: String
    val playerId: Int
    val fields: List<String>
    val masterSession: MasterSession
    val worldSession: WorldSessionImpl?
}

interface MasterSession {
    val playerId: Int
    val token: String
    val name: String
    val locale: String
    val guest: Boolean
    val worlds: Worlds
}

interface Worlds {
    val active: List<World>
    val suggested: List<World>
    val other: List<World>
    val prereg: List<World>
}

interface World {
    val serverName: String
    val name: String
    val rank: String
    val url: String
    val register: Boolean
    val login: Boolean
    val best: Boolean
    val active: String
}

interface WorldSession {
    val serverName: String?
    val token: String?
}