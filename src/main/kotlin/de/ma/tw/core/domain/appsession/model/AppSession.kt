package de.ma.tw.core.domain.appsession.model

import de.ma.tw.core.domain.appsession.api.signon.World
import java.time.LocalDateTime
import java.util.*

interface AppSession {
    val id: UUID?
    val playerId: Int
    val token: String
    val signOnDateTime: LocalDateTime
    val worlds: List<World>
}