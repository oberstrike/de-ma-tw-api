package de.ma.tw.app.persistence.account

import de.ma.tw.app.persistence.account.model.AccountRepository
import de.ma.tw.app.web.apis.AccountResource
import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.utils.database.DatabaseTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject


@QuarkusTest
@QuarkusTestResource(DatabaseTestResource::class)
class AccountGatewayImplTest {

    @Inject
    private lateinit var accountGatewayImpl: AccountGatewayImpl

    @Inject
    private lateinit var accountRepository: AccountRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @TestScenario(scenario = OneAccountTestScenario::class)
    fun createAccountNegativeTest(scenario: OneAccountTestScenario) = runTest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createAccountTest() = runTest {
        val username = "test"
        val password = "test"

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @TestScenario(scenario = OneAccountTestScenario::class)
    fun findByIdTest(scenario: OneAccountTestScenario) = runTest {

        val result = accountGatewayImpl.findById(scenario.account.id)

        result `should not be` null
        result!!.username shouldBeEqualTo scenario.account.username
        result.password shouldBeEqualTo scenario.account.password

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @TestScenario(scenario = OneAccountTestScenario::class)
    fun findAllTest(scenario: OneAccountTestScenario) = runTest {

        val result = accountGatewayImpl.findAll()

        result.shouldNotBeEmpty()

        val accountOverview = result.first()

        accountOverview.id `should be equal to` scenario.account.id
        accountOverview.username `should be equal to` scenario.account.username
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @TestScenario(scenario = OneAccountTestScenario::class)
    fun deleteByIdTest(scenario: OneAccountTestScenario) = runTest {

        accountRepository.count().awaitSuspending() `should be equal to` 1

        accountGatewayImpl.deleteById(scenario.account.id)

        accountRepository.count().awaitSuspending() `should be equal to` 0
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteByIdNegativeTest() = runTest {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            runBlocking {
                accountGatewayImpl.deleteById(UUID.fromString("b62dbfcc-6c73-4a42-96ea-c968e8e3267e"))

            }
        }

    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @TestScenario(scenario = OneAccountTestScenario::class)
    fun existsByNameTest(scenario: OneAccountTestScenario) = runTest {

        val existsByName = accountGatewayImpl.existsByName(scenario.account.username)

        existsByName `should be equal to` true

        val doesntExistByName = accountGatewayImpl.existsByName("not_exists")

        doesntExistByName `should be equal to` false
    }
}