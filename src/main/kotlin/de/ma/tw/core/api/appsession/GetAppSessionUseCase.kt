package de.ma.tw.core.api.appsession

import de.ma.tw.core.domain.appsession.model.AppSession
import java.util.UUID

interface GetAppSessionUseCase {
    suspend operator fun invoke(sessionId: UUID): AppSession?
}