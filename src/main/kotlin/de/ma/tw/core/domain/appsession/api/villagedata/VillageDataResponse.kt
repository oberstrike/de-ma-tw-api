package de.ma.tw.core.domain.appsession.api.villagedata

interface VillageDataResponse {
    val wood: Int
    val stone: Int
    val iron: Int
    val woodProd: Int
    val stoneProd: Int
    val ironProd: Int
    val maxStorage: Int
    val pop: Int
    val popMax: Int
}
