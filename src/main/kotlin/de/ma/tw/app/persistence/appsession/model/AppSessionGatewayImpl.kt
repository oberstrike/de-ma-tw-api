package de.ma.tw.app.persistence.appsession.model

import de.ma.tw.core.domain.appsession.AppSessionGateway
import de.ma.tw.core.domain.appsession.model.AppSession
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AppSessionGatewayImpl: AppSessionGateway {

    override fun persist(appSession: AppSession): UUID {
        TODO("Not yet implemented")
    }

}