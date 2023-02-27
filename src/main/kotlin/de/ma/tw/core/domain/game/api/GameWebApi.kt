package de.ma.tw.core.domain.game.api

interface GameWebApi {
    fun login(loginUrl: String): Any
}