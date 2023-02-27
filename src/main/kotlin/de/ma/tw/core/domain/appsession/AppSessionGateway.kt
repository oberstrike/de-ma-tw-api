package de.ma.tw.core.domain.appsession

import de.ma.tw.core.domain.appsession.model.AppSession
import java.util.*

interface AppSessionGateway {
    suspend fun persist(appSession: AppSession): UUID
    suspend fun findById(sessionId: UUID): AppSession?
}