package de.ma.tw.app.web.config

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithName


@ConfigMapping(prefix = "liquibase")
interface LiquibaseConfig {

    @WithName("url")
    fun url(): String?
}