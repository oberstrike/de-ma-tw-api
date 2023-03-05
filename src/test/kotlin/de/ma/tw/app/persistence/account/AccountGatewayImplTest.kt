package de.ma.tw.app.persistence.account

import de.ma.tw.app.persistence.account.model.AccountEntity
import de.ma.tw.app.persistence.account.model.AccountRepository
import de.ma.tw.app.persistence.appsession.model.AppSessionEntity
import de.ma.tw.app.persistence.appsession.model.AppSessionRepository
import de.ma.tw.app.web.apis.AccountResource
import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.core.domain.account.model.AccountUpdate
import de.ma.tw.utils.database.DatabaseTestResource
import de.ma.tw.utils.sql.AbstractGatewayTest
import de.ma.tw.utils.sql.Sql
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@QuarkusTest
@QuarkusTestResource(DatabaseTestResource::class)
class AccountGatewayImplTest : AbstractGatewayTest() {

    @Inject
    private lateinit var accountGatewayImpl: AccountGatewayImpl

    @Inject
    private lateinit var accountRepository: AccountRepository

    @Inject
    private lateinit var appSessionRepository: AppSessionRepository


    @Test
    fun createAccountNegativeTest() {

        runLocalTest(OneAccountTestScenario) { scenario ->
            val username = scenario.account.username
            val password = "test"

            val accountCreate = AccountResource.AccountCreateImpl(
                username,
                password
            )

            assertThrows(
                IllegalArgumentException::class.java
            ) {
                runBlocking {
                    accountGatewayImpl.createAccount(accountCreate)
                }
            }
        }
    }


    @Test
    fun createAccountTest() = runLocalTest {
        val username = "test"
        val password = "test"

        val accountEntities = accountRepository.findAll().list<AccountEntity>().awaitSuspending()
        println("Count before ${accountEntities.map { it.username }}")

        val accountCreate = AccountResource.AccountCreateImpl(
            username,
            password
        )

        val createAccount = accountGatewayImpl.createAccount(accountCreate)

        createAccount shouldNotBe null
        createAccount.accountId shouldNotBe null

        val count = accountRepository.findAll().count().awaitSuspending()

        assertEquals(1, count)
    }


    @Test
    fun findByIdTest() = runLocalTest(OneAccountTestScenario) { scenario ->

        val result = accountGatewayImpl.findById(scenario.account.id)

        result `should not be` null
        result!!.username shouldBeEqualTo scenario.account.username
        result.password shouldBeEqualTo scenario.account.password

    }


    @Test
    fun findAllTest() = runLocalTest(OneAccountTestScenario) { scenario ->

        val result = accountGatewayImpl.findAll()

        result.shouldNotBeEmpty()

        val accountOverview = result.first()

        accountOverview.id `should be equal to` scenario.account.id
        accountOverview.username `should be equal to` scenario.account.username
    }


    @Test
    fun deleteByIdTest() = runLocalTest(OneAccountTestScenario) { scenario ->

        accountRepository.count().awaitSuspending() `should be equal to` 1

        accountGatewayImpl.deleteById(scenario.account.id)

        accountRepository.count().awaitSuspending() `should be equal to` 0
    }


    @Test
    fun deleteByIdNegativeTest() = runLocalTest {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            runBlocking {
                accountGatewayImpl.deleteById(UUID.fromString("b62dbfcc-6c73-4a42-96ea-c968e8e3267e"))

            }
        }

    }


    @Test
    fun existsByNameTest() = runLocalTest(OneAccountTestScenario) { scenario ->

        val existsByName = accountGatewayImpl.existsByName(scenario.account.username)

        existsByName `should be equal to` true

        val doesntExistByName = accountGatewayImpl.existsByName("not_exists")

        doesntExistByName `should be equal to` false
    }


    @Test
    fun updateTest() = runLocalTest(OneAccountTestScenario) { scenario ->
        val accountId = scenario.account.id
        val appSessionEntity = Panache.withTransaction {
            appSessionRepository.persist(AppSessionEntity().apply {
                this.playerId = 1
                this.token = "token"
                this.signOnDateTime = LocalDateTime.now()
                this.accountEntity = AccountEntity().apply {
                    this.id = accountId
                }
            })
        }.awaitSuspending()

        accountGatewayImpl.update(
            accountId,
            object : AccountUpdate {
                override val appSessionId: UUID = appSessionEntity.id!!
            }
        )


        val result = accountRepository.findById(accountId).awaitSuspending()
        result.appSessionEntity shouldNotBe null
        result.appSessionEntity!!.id `should be equal to` appSessionEntity.id
        result.appSessionEntity!!.accountEntity!!.id `should be equal to` accountId
    }

}