package de.ma.tw.core.domain.appsession.api

import de.ma.tw.core.domain.appsession.api.signon.SignOnForm
import de.ma.tw.core.domain.appsession.api.signon.SignOnResponse

interface AppSessionApi {
    suspend fun signOn(signOnForm: SignOnForm): SignOnResponse
}