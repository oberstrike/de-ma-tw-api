package de.ma.tw.app.persistence.account

import de.ma.tw.app.persistence.account.model.AccountEntity
import de.ma.tw.app.persistence.account.model.AccountRepository
import de.ma.tw.app.persistence.appsession.model.AppSessionEntity
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.app.web.shared.UUIDSerializer
import de.ma.tw.core.domain.account.model.*
import io.quarkus.hibernate.reactive.panache.Panache
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.serialization.Serializable
import java.util.*
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class AccountGatewayImpl(
    private val accountRepository: AccountRepository
) : AccountGateway {

    @Serializable
    data class AccountCreatedResponseImpl(
        @Serializable(with = UUIDSerializer::class)
        override val accountId: UUID
    ) : AccountCreatedResponse

    @Serializable
    data class AccountImpl(
        override val username: String,
        override val password: String,
        @Serializable(with = UUIDSerializer::class)
        override val appSessionId: UUID? = null
    ) : Account

    @Serializable
    data class AccountOverviewImpl(
        override val username: String,
        @Serializable(with = UUIDSerializer::class)
        override val id: UUID
    ) : AccountOverview

    override suspend fun createAccount(accountCreate: AccountCreate): AccountCreatedResponse {
        if (existsByName(accountCreate.username)) {
            throw IllegalArgumentException("An account with the name ${accountCreate.username} does already exists.")
        }

        val accountEntity = AccountEntity()
        accountEntity.username = accountCreate.username
        accountEntity.password = accountCreate.password

        val persisted = Panache.withTransaction {
            accountRepository.persist(
                accountEntity
            )
        }.awaitSuspending()

        return AccountCreatedResponseImpl(persisted.id!!)

    }

    override suspend fun findById(accountId: UUID): Account? {
        val accountEntity: AccountEntity = accountRepository.findById(accountId).awaitSuspending() ?: return null

        return AccountImpl(accountEntity.username, accountEntity.password, accountEntity.appSessionEntity?.id)

    }

    override suspend fun findAll(): List<AccountOverview> {
        return accountRepository.findAll().list<AccountEntity>().awaitSuspending()
            .map { AccountOverviewImpl(it.username, it.id!!) }

    }

    override suspend fun deleteById(accountId: UUID) {

        val deleted = Panache.withTransaction {
            accountRepository.deleteById(accountId)
        }.awaitSuspending()

        if (!deleted) {
            throw IllegalArgumentException("There was an error while deleting the account: $accountId")
        }
    }

    override suspend fun existsByName(username: String): Boolean {
        return accountRepository.count("username = ?1", username).awaitSuspending() != 0L

    }

    override suspend fun update(accountId: UUID, accountUpdate: AccountUpdate) {
        val accountEntity = accountRepository.findById(accountId).awaitSuspending()

        accountEntity.appSessionEntity = AppSessionEntity().apply {
            this.id = accountUpdate.appSessionId
            this.accountEntity = accountEntity
        }

        val result = Panache.withTransaction {
            accountRepository.persist(accountEntity)
        }.awaitSuspending()

        println(result.appSessionEntity)
    }
}