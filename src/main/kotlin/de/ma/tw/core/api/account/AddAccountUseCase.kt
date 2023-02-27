package de.ma.tw.core.api.account

import de.ma.tw.core.domain.account.model.AccountCreate
import de.ma.tw.core.domain.account.model.AccountCreatedResponse

interface AddAccountUseCase {
    suspend operator fun invoke(accountCreate: AccountCreate): AccountCreatedResponse
}