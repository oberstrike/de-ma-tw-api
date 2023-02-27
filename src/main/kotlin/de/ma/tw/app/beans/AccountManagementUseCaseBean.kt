package de.ma.tw.app.beans

import de.ma.tw.core.api.account.AccountManagementUseCase
import de.ma.tw.core.api.shared.EncryptionService
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.impl.account.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AccountManagementUseCaseBean(
    accountGateway: AccountGateway,
    encryptionService: EncryptionService
) : AccountManagementUseCase by AccountManagementUseCaseImpl(
    addAccountUseCase = AddAccountUseCaseImpl(accountGateway, encryptionService),
    deleteAccountUseCase = DeleteAccountUseCaseImpl(accountGateway),
    getAccountsUseCase = GetAccountsUseCaseImpl(accountGateway),
    getAccountUseCase = GetAccountUseCaseImpl(accountGateway)
)