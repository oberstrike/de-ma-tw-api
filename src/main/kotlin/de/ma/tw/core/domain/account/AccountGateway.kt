package de.ma.tw.core.domain.account

import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse
import de.ma.tw.core.domain.account.model.AccountOverview
import java.util.*

interface AccountGateway {
    suspend fun createAccount(accountCreate: AccountCreate): AccountCreatedResponse
    suspend fun findById(accountId: UUID): Account?
    suspend fun findAll(): List<AccountOverview>
    suspend fun deleteById(accountId: UUID)
    suspend fun existsByName(username: String): Boolean

}