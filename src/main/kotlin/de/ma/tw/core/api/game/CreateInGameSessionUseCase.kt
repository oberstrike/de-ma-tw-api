package de.ma.tw.core.api.game

import de.ma.tw.core.domain.game.model.GameSession
import java.util.UUID

interface CreateInGameSessionUseCase {
    suspend fun invoke(sessionId: UUID, serverName: String): UUID
}