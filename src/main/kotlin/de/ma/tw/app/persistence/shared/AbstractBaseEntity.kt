package de.ma.tw.app.persistence.shared

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*

@MappedSuperclass
abstract class AbstractBaseEntity {

    @get:Id
    @get:GeneratedValue(generator = "UUID")
    @get:GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id: UUID? = null


}