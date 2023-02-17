package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.core.domain.appsession.model.AppSession
import java.time.LocalDateTime
import java.util.*

class AppSessionEntity: AppSession {

    override val id: UUID? = null

    override val playerId: Int = 0

    override val signOnDateTime: LocalDateTime
        get() = TODO("Not yet implemented")

    override val token: String
        get() = TODO("Not yet implemented")
}