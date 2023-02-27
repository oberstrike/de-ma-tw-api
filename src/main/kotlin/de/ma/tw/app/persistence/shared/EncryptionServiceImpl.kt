package de.ma.tw.app.persistence.shared

import de.ma.tw.core.api.shared.EncryptionService
import java.util.Base64
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EncryptionServiceImpl : EncryptionService {

    override fun decrypt(password: String): String {
        return String(Base64.getDecoder().decode(password.toByteArray()))
    }

    override fun encrypt(password: String): String {
        return Base64.getEncoder().encodeToString(password.toByteArray())
    }

}