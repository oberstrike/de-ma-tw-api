package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.app.persistence.account.model.AccountEntity
import de.ma.tw.app.persistence.shared.AbstractBaseEntity
import de.ma.tw.core.domain.appsession.api.signon.World
import de.ma.tw.core.domain.appsession.model.AppSession
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class AppSessionEntity : AbstractBaseEntity() {

    var playerId: Int = 0

    var signOnDateTime: LocalDateTime = LocalDateTime.now()

    var token: String = ""

    @get:OneToOne
    @get:JoinColumn(name = "accountEntity")
    var accountEntity: AccountEntity? = null

}