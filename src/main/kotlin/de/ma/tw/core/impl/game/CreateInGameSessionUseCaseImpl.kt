package de.ma.tw.core.impl.game

import de.ma.tw.core.api.game.CreateInGameSessionUseCase
import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.AppGameApi
import de.ma.tw.core.domain.appsession.api.login.LoginForm
import de.ma.tw.core.domain.appsession.model.AppSession
import de.ma.tw.core.domain.game.GameSessionGateway
import de.ma.tw.core.domain.game.api.GameWebApi
import de.ma.tw.core.domain.game.model.GameSession
import java.util.*
import javax.crypto.Cipher

class CreateInGameSessionUseCaseImpl(
    private val gameApi: AppGameApi,
    private val gameWebApi: GameWebApi,
    private val appSessionGateway: AppSessionGateway,
    private val gameSessionGateway: GameSessionGateway
) : CreateInGameSessionUseCase {


    data class LoginFormImpl(
        override val token: String
    ) : LoginForm

    data class GameSessionImpl(
        override val id: UUID?
    ) : GameSession

    override suspend fun invoke(sessionId: UUID, serverName: String): UUID {
        val session: AppSession = appSessionGateway.findById(sessionId)
            ?: throw IllegalArgumentException("No session with $sessionId was found.")

        val oldGameSession: GameSession? = gameSessionGateway.findBySessionAndServerName(sessionId, serverName)

        if (oldGameSession != null) {
            return oldGameSession.id!!
        }


        val world = session.worlds.find {
            it.serverName == serverName
        } ?: throw IllegalArgumentException("No world with: $serverName was found.")

        val serverUrl = world.url

        val loginResponse = gameApi.login(
            serverUrl,
            LoginFormImpl(
                session.token
            )
        )

        val sid = loginResponse.sid
        val loginUrl = loginResponse.loginUrl

        val cookies = gameWebApi.login(
            loginUrl
        )

        val gameSession: GameSession = gameSessionGateway.persist(
            GameSessionImpl(
                null
            )
        )

        return gameSession.id!!
    }

}