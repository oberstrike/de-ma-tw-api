package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.app.persistence.shared.AbstractBaseEntity
import de.ma.tw.core.domain.appsession.api.signon.World
import de.ma.tw.core.domain.appsession.model.AppSession
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity

@Entity
class AppSessionEntity : AbstractBaseEntity() {

    var playerId: Int = 0

    var signOnDateTime: LocalDateTime = LocalDateTime.now()

    var token: String = ""

}