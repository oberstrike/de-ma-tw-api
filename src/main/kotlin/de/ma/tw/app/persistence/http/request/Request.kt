package de.ma.tw.app.persistence.http.request

import kotlinx.serialization.json.JsonArray

data class Request(
    val form: JsonArray,
    val hash: String
)