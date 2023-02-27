package de.ma.tw.app.persistence.appsession.api

import de.ma.tw.core.domain.appsession.api.villagedata.VillageDataResponse
import kotlinx.serialization.SerialName

data class VillageDataResult(
    val result: VillageDataResponseImpl
)


data class VillageDataResponseImpl(
    @SerialName("iron_prod")
    override val ironProd: Int,
    @SerialName("max_storage")
    override val maxStorage: Int,
    override val pop: Int,
    @SerialName("pop_max")
    override val popMax: Int,
    override val stone: Int,
    @SerialName("stone_prod")
    override val stoneProd: Int,
    override val wood: Int,
    @SerialName("wood_prod")
    override val woodProd: Int,
    override val iron: Int
) : VillageDataResponse