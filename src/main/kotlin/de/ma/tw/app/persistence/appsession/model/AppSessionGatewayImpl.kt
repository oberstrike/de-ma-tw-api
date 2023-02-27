package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.api.signon.World
import de.ma.tw.core.domain.appsession.model.AppSession
import io.quarkus.hibernate.reactive.panache.Panache
import io.smallrye.mutiny.coroutines.awaitSuspending
import java.time.LocalDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AppSessionGatewayImpl(
    private val appSessionRepository: AppSessionRepository
) : AppSessionGateway {

    data class AppSessionImpl(
        override val token: String,
        override val signOnDateTime: LocalDateTime,
        override val playerId: Int,
        override val worlds: List<World>,
        override val id: UUID?
    ) : AppSession

    override suspend fun persist(appSession: AppSession): UUID {
        val appSessionEntity = AppSessionEntity()
        appSessionEntity.playerId = appSession.playerId
        appSessionEntity.token = appSession.token
        appSessionEntity.signOnDateTime = LocalDateTime.now()

        val sessionEntity = Panache.withTransaction {
            appSessionRepository.persist(appSessionEntity)
        }.awaitSuspending()

        return sessionEntity.id!!
    }

    override suspend fun findById(sessionId: UUID): AppSession? {
        return this.appSessionRepository.findById(sessionId).awaitSuspending().let {
            AppSessionImpl(it.token, it.signOnDateTime, it.playerId, emptyList(), it.id)
        }
    }

}