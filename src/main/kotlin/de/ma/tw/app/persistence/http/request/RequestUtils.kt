package de.ma.tw.app.persistence.http.request

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object RequestUtils {

    fun createRequest(form: List<JsonElement>): Request {
        val jsonArray = JsonArray(form)
        return Request(
            jsonArray,
            toHash(Json.encodeToString(jsonArray))
        )
    }

    private fun toHash(form: String): String {
        return hash("2sB2jaeNEG6C01QOTldcgCKO-${form}")
    }

    private fun hash(str: String): String {
        val stringBuffer = StringBuilder()
        try {
            for (b in MessageDigest.getInstance("SHA1").digest(str.toByteArray())) {
                stringBuffer.append(((b.toInt() and 255) + 256).toString(16).substring(1))
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return stringBuffer.toString()
    }

}