package de.ma.tw.core.domain.appsession

import de.ma.tw.core.domain.appsession.model.AppSession
import java.util.*

interface AppSessionGateway {
    fun persist(appSession: AppSession): UUID
}