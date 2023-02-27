package de.ma.tw.app.persistence.account.model

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import java.util.UUID
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class AccountRepository : PanacheRepositoryBase<AccountEntity, UUID> {
}