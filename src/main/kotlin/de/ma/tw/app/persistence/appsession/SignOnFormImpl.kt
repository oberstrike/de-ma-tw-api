package de.ma.tw.app.persistence.appsession

import de.ma.tw.core.domain.appsession.api.SignOnForm
import kotlinx.serialization.Serializable

@Serializable
data class SignOnFormImpl(
    override val intent: String,
    override val username: String,
    override val password: String
) : SignOnForm