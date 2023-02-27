package de.ma.tw.core.impl.account

import de.ma.tw.core.api.account.*
import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse
import de.ma.tw.core.domain.account.model.AccountOverview
import java.util.*

class AccountManagementUseCaseImpl(
    private val addAccountUseCase: AddAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : AccountManagementUseCase {

    override suspend fun addAccount(accountCreate: AccountCreate): AccountCreatedResponse {
        return this.addAccountUseCase(accountCreate)
    }

    override suspend fun deleteAccountById(accountId: UUID) {
        deleteAccountUseCase(accountId)
    }

    override suspend fun getAccountById(accountId: UUID): Account? {
        return getAccountUseCase(accountId)
    }

    override suspend fun getAccounts(): List<AccountOverview> {
        return getAccountsUseCase()
    }
}