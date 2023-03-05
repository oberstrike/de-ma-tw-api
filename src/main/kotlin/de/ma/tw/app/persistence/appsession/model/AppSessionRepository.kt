package de.ma.tw.app.persistence.appsession.model

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AppSessionRepository : PanacheRepositoryBase<AppSessionEntity, UUID> {

}