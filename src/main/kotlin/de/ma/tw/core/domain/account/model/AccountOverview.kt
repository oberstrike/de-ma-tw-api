package de.ma.tw.core.domain.account.model

import java.util.UUID

interface AccountOverview {
    val id: UUID
    val username: String
}