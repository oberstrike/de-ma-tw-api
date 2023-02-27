package de.ma.tw.app.web.apis

import de.ma.tw.app.persistence.account.AccountGatewayImpl
import de.ma.tw.core.api.account.AccountManagementUseCase
import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import de.ma.tw.core.api.appsession.DeleteAppSessionUseCase
import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import kotlinx.serialization.Serializable
import java.util.UUID
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/account")
class AccountResource(
    private val accountManagementUseCase: AccountManagementUseCase,
    private val createAppSessionUseCase: CreateAppSessionUseCase
) {
    @Serializable
    data class AccountCreateImpl(
        override val username: String,
        override val password: String
    ) : AccountCreate

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createAccount(accountCreate: AccountCreateImpl): AccountGatewayImpl.AccountCreatedResponseImpl {
        return accountManagementUseCase.addAccount(accountCreate) as AccountGatewayImpl.AccountCreatedResponseImpl
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAccount(
        @PathParam("accountId") accountId: UUID
    ): AccountGatewayImpl.AccountImpl? {
        return accountManagementUseCase.getAccountById(accountId) as AccountGatewayImpl.AccountImpl
    }

    @GET
    suspend fun getAccounts(): List<AccountGatewayImpl.AccountOverviewImpl> {
        return accountManagementUseCase.getAccounts().filterIsInstance<AccountGatewayImpl.AccountOverviewImpl>()
    }

    @DELETE
    @Path("/{accountId}")
    suspend fun deleteAccount(
        @PathParam("accountId") accountId: UUID
    ) {
        accountManagementUseCase.deleteAccountById(accountId)
    }


    @Path("/{accountId}/session")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createSession(
        @PathParam("accountId") accountId: UUID,
    ): AppSessionCreationResult {

        return createAppSessionUseCase(accountId)
    }

    @Path("/{accountId}/session")
    @DELETE
    suspend fun deleteSession(
        @PathParam("accountId") accountId: UUID,
    ) {
    }

}