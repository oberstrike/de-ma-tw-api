package de.ma.tw.app.persistence.account.model

import de.ma.tw.app.persistence.appsession.model.AppSessionEntity
import de.ma.tw.app.persistence.shared.AbstractBaseEntity
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class AccountEntity : AbstractBaseEntity() {

    var username: String = ""

    var password: String = ""

    @get:OneToOne(mappedBy = "accountEntity")
    var appSessionEntity: AppSessionEntity? = null

}