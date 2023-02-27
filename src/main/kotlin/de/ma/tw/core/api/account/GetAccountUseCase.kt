package de.ma.tw.core.api.account

import de.ma.tw.core.domain.account.model.Account

interface GetAccountUseCase {
    suspend operator fun invoke(accountId: java.util.UUID): Account?
}