package de.ma.tw.core.impl.account

import de.ma.tw.core.api.account.AddAccountUseCase
import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import de.ma.tw.core.api.shared.EncryptionService
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse

class AddAccountUseCaseImpl(
    private val accountGateway: AccountGateway,
    private val encryptionService: EncryptionService
) : AddAccountUseCase {

    private data class AccountCreateImpl(
        override val username: String,
        override val password: String
    ) : AccountCreate

    override suspend fun invoke(accountCreate: AccountCreate): AccountCreatedResponse {
        val existsByName: Boolean = accountGateway.existsByName(accountCreate.username)
        if (existsByName) {
            throw IllegalArgumentException("There is a already an account with the name: ${accountCreate.username}")
        }


        val encryptedPassword = encryptionService.encrypt(accountCreate.password)

        return accountGateway.createAccount(
            AccountCreateImpl(
                username = accountCreate.username,
                password = encryptedPassword
            )
        )

    }
}