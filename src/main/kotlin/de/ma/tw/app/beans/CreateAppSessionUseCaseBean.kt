package de.ma.tw.app.beans

import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import de.ma.tw.core.api.shared.EncryptionService
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.AppSessionApi
import de.ma.tw.core.impl.appsession.CreateAppSessionUseCaseImpl
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class CreateAppSessionUseCaseBean(
    appSessionApi: AppSessionApi,
    appSessionGateway: AppSessionGateway,
    encryptionService: EncryptionService,
    accountGateway: AccountGateway
) : CreateAppSessionUseCase by CreateAppSessionUseCaseImpl(
    appSessionApi = appSessionApi,
    appSessionGateway = appSessionGateway,
    encryptionService = encryptionService,
    accountGateway = accountGateway
) {
}