package de.ma.tw.app.web.apis

import de.ma.tw.app.web.utils.IScenario
import de.ma.tw.app.web.utils.TestScenario
import de.ma.tw.core.api.account.AccountManagementUseCase
import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import io.mockk.impl.annotations.InjectMockKs
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.enterprise.inject.spi.CDI


@QuarkusTest
@TestHTTPEndpoint(AccountResource::class)
class AccountResourceTest {

    @InjectMockKs
    lateinit var accountManagementUseCase: AccountManagementUseCase

    @InjectMockKs
    lateinit var createAppSessionUseCase: CreateAppSessionUseCase

    @Test
    fun createAccount() {

    }

    @Test
    fun getAccount() {
    }

    @Test
    fun getAccounts() {
    }

    @Test
    fun deleteAccount() {
    }

    @Test
    fun createSession() {
    }

    @Test
    fun deleteSession() {
    }
}