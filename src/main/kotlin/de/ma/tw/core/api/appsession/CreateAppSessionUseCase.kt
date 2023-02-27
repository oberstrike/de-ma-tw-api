package de.ma.tw.core.api.appsession

import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import java.util.UUID

interface CreateAppSessionUseCase {
    suspend operator fun invoke(accountId: UUID):
            AppSessionCreationResult

}