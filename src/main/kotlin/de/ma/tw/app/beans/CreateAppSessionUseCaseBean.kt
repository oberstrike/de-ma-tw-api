package de.ma.tw.app.beans

import de.ma.tw.core.api.session.CreateAppSessionUseCase
import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.AppSessionApi
import de.ma.tw.core.impl.appsession.CreateAppAppSessionUseCaseImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateAppSessionUseCaseBean(
    appSessionApi: AppSessionApi,
    appSessionGateway: AppSessionGateway
) : CreateAppSessionUseCase by CreateAppAppSessionUseCaseImpl(
    appSessionApi = appSessionApi,
    appSessionGateway = appSessionGateway
) {
}