package de.ma.tw.core.api.account

import java.util.UUID

interface DeleteAccountUseCase {
    suspend operator fun invoke(accountId: UUID)
}