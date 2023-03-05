package de.ma.tw.core.domain.account

import de.ma.tw.core.domain.account.model.*
import de.ma.tw.core.impl.appsession.CreateAppSessionUseCaseImpl
import java.util.*

interface AccountGateway {
    suspend fun createAccount(accountCreate: AccountCreate): AccountCreatedResponse
    suspend fun findById(accountId: UUID): Account?
    suspend fun findAll(): List<AccountOverview>
    suspend fun deleteById(accountId: UUID)
    suspend fun existsByName(username: String): Boolean
    suspend fun update(accountId: UUID, accountUpdate: AccountUpdate)

}