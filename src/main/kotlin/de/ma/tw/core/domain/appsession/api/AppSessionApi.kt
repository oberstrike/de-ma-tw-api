package de.ma.tw.core.domain.appsession.api

interface AppSessionApi {
    suspend fun signOn(signOnForm: SignOnForm): SignOnResponse
}