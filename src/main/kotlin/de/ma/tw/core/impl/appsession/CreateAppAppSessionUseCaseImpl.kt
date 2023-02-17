package de.ma.tw.core.impl.appsession

import de.ma.tw.core.api.session.CreateAppSessionUseCase
import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.AppSessionApi
import de.ma.tw.core.domain.appsession.api.SignOnForm
import de.ma.tw.core.domain.appsession.api.Worlds
import de.ma.tw.core.domain.appsession.model.AppSession
import de.ma.tw.core.domain.appsession.model.AppSessionCreateParams
import de.ma.tw.core.domain.appsession.model.AppSessionCreationResult
import java.time.LocalDateTime
import java.util.*

class CreateAppAppSessionUseCaseImpl(
    private val appSessionApi: AppSessionApi,
    private val appSessionGateway: AppSessionGateway
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
        override val signOnDateTime: LocalDateTime
    ) : AppSession

    private data class SignOnCreateResultImpl(
        override val worlds: Worlds,
        override val sessionId: UUID,
        override val playerId: Int,
        override val playerName: String
    ) : AppSessionCreationResult

    override suspend fun invoke(appSessionCreateParams: AppSessionCreateParams): AppSessionCreationResult {

        //Create signOnForm
        val signOnForm = SignOnFormImpl(
            "login",
            appSessionCreateParams.username,
            appSessionCreateParams.password
        )

        //Use the api to get the response
        val signOnResponse = appSessionApi.signOn(signOnForm)

        //create the AppSessionModel
        val appSession = AppSessionImpl(
            id = null,
            token = signOnResponse.masterSession.token,
            playerId = signOnResponse.playerId,
            signOnDateTime = LocalDateTime.now()
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