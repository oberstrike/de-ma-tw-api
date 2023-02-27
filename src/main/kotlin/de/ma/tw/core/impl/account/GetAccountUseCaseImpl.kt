package de.ma.tw.core.impl.account

import de.ma.tw.core.api.account.GetAccountUseCase
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.domain.account.model.Account
import java.util.*

class GetAccountUseCaseImpl(
    private val accountGateway: AccountGateway
) : GetAccountUseCase {
    override suspend fun invoke(accountId: UUID): Account? {
        return accountGateway.findById(accountId)
    }
}