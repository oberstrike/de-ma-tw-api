package de.ma.tw.core.domain.account.model

import java.util.UUID

interface Account {
    val username: String
    val password: String
    val appSessionId: UUID?
}