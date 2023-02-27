package de.ma.tw.app.persistence.account.model

import de.ma.tw.app.persistence.shared.AbstractBaseEntity
import javax.persistence.Entity

@Entity
class AccountEntity : AbstractBaseEntity() {

    var username: String = ""

    var password: String = ""

}