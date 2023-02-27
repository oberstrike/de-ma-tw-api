package de.ma.tw.core.impl.account

import de.ma.tw.core.api.account.DeleteAccountUseCase
import de.ma.tw.core.domain.account.AccountGateway
import io.quarkus.hibernate.reactive.panache.Panache
import java.util.*

class DeleteAccountUseCaseImpl(
    private val accountGateway: AccountGateway
) : DeleteAccountUseCase {
    override suspend fun invoke(accountId: UUID) {
        val persistedAccount = accountGateway.findById(accountId)
            ?: throw IllegalArgumentException("No account with $accountId was found")

        accountGateway.deleteById(accountId)

    }
}