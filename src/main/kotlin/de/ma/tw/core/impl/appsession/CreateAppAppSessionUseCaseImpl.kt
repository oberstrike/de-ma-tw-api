package de.ma.tw.core.impl.appsession

import de.ma.tw.core.api.appsession.CreateAppSessionUseCase
import de.ma.tw.core.api.shared.EncryptionService
import de.ma.tw.core.domain.account.AccountGateway
import de.ma.tw.core.domain.account.model.Account
import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.AppSessionApi
import de.ma.tw.core.domain.appsession.api.signon.SignOnForm
import de.ma.tw.core.domain.appsession.api.signon.World
import de.ma.tw.core.domain.appsession.api.signon.Worlds
import de.ma.tw.core.domain.appsession.model.AppSession
import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import java.time.LocalDateTime
import java.util.*

class CreateAppAppSessionUseCaseImpl(
    private val appSessionApi: AppSessionApi,
    private val appSessionGateway: AppSessionGateway,
    private val encryptionService: EncryptionService,
    private val accountGateway: AccountGateway
) : CreateAppSessionUseCase {

    private data class SignOnFormImpl(
        override val intent: String,
        override val username: String,
        override val password: String
    ) : SignOnForm

    private data class AppSessionImpl(
        override val id: UUID?,
        override val token: String,
        override val playerId: Int,
        override val signOnDateTime: LocalDateTime,
        override val worlds: List<World>
    ) : AppSession

    private data class SignOnCreateResultImpl(
        override val worlds: Worlds,
        override val sessionId: UUID,
        override val playerId: Int,
        override val playerName: String
    ) : AppSessionCreationResult

    override suspend fun invoke(accountId: UUID): AppSessionCreationResult {

        val account: Account? = accountGateway.findById(accountId)?: throw IllegalArgumentException("")

        //Create signOnForm
        val signOnForm = SignOnFormImpl(
            "login",
            account!!.username,
            encryptionService.decrypt(account.password)
        )

        //Use the api to get the response
        val signOnResponse = appSessionApi.signOn(signOnForm)

        //create the AppSessionModel
        val appSession = AppSessionImpl(
            id = null,
            token = signOnResponse.masterSession.token,
            playerId = signOnResponse.playerId,
            signOnDateTime = LocalDateTime.now(),
            worlds = signOnResponse.masterSession.worlds.active
        )

        //Persist the AppSessionModel
        val sessionId = appSessionGateway.persist(appSession)

        return SignOnCreateResultImpl(
            sessionId = sessionId,
            worlds = signOnResponse.masterSession.worlds,
            playerId = signOnResponse.playerId,
            playerName = signOnResponse.masterSession.name
        )
    }

}