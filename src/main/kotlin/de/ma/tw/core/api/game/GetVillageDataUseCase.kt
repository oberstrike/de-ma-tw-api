package de.ma.tw.core.api.game

import de.ma.tw.core.domain.appsession.api.villagedata.VillageDataResponse
import java.util.UUID

interface GetVillageDataUseCase {

    suspend operator fun invoke(
        sessionId: UUID,
        wordId: UUID,
        villageId: Int
    ): VillageDataResponse

}