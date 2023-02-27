package de.ma.tw.core.domain.appsession.api

import de.ma.tw.core.domain.appsession.api.login.LoginForm
import de.ma.tw.core.domain.appsession.api.login.LoginResponse
import de.ma.tw.core.domain.appsession.api.villagedata.VillageDataForm
import de.ma.tw.core.domain.appsession.api.villagedata.VillageDataResponse

interface AppGameApi {
    suspend fun login(serverUrl: String, loginForm: LoginForm): LoginResponse

    suspend fun getVillageData(villageDataForm: VillageDataForm): VillageDataResponse
}