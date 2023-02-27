package de.ma.tw.core.api.account

import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.account.model.AccountOverview

interface GetAccountsUseCase {
    suspend operator fun invoke(): List<AccountOverview>
}