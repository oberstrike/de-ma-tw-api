package de.ma.tw.core.api.appsession

import java.util.UUID

interface DeleteAppSessionUseCase {

    suspend operator fun invoke(accountId: UUID)

}