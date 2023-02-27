package de.ma.tw.core.domain.appsession.model

import de.ma.tw.core.domain.appsession.api.signon.World
import de.ma.tw.core.domain.appsession.api.signon.Worlds
import java.util.UUID

interface AppSessionCreationResult {
    val sessionId: UUID
    val worlds: Worlds
    val playerId: Int
    val playerName: String
}