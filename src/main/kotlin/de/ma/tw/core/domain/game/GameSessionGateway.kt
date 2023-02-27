package de.ma.tw.core.domain.game

import de.ma.tw.core.domain.game.model.GameSession
import de.ma.tw.core.impl.game.CreateInGameSessionUseCaseImpl
import java.util.*

interface GameSessionGateway {
    fun findBySessionAndServerName(sessionId: UUID, serverName: String): GameSession?
    fun persist(gameSessionImpl: CreateInGameSessionUseCaseImpl.GameSessionImpl): GameSession
}