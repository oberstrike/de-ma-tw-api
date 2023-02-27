package de.ma.tw.core.api.shared

interface EncryptionService {
    fun encrypt(password: String): String
    fun decrypt(password: String): String
}