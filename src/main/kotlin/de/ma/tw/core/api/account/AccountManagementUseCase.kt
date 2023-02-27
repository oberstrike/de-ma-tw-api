package de.ma.tw.core.api.account

import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse
import de.ma.tw.core.domain.account.model.AccountOverview
import java.util.*

interface AccountManagementUseCase {

    suspend fun deleteAccountById(accountId: UUID)

    suspend fun getAccounts(): List<AccountOverview>

    suspend fun getAccountById(accountId: UUID): Account?
    suspend fun addAccount(accountCreate: AccountCreate): AccountCreatedResponse
}