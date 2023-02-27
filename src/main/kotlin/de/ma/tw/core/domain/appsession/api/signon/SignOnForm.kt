package de.ma.tw.core.domain.appsession.api.signon


interface SignOnForm {
    val username: String
    val password: String
    val intent: String
}
