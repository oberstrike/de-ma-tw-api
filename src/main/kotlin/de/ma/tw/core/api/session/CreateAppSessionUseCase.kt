package de.ma.tw.core.api.session

import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult

interface CreateAppSessionUseCase {
    suspend operator fun invoke(appSessionCreateParams: AppSessionCreateParams):
            AppSessionCreationResult

}