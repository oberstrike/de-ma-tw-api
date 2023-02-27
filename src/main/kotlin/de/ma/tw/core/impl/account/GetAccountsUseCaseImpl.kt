package de.ma.tw.core.impl.account

import de.ma.tw.core.api.account.GetAccountsUseCase
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.domain.account.model.AccountOverview

class GetAccountsUseCaseImpl(
    private val accountGateway: AccountGateway
) : GetAccountsUseCase{

    override suspend fun invoke(): List<AccountOverview> {
        return accountGateway.findAll()
    }
}
