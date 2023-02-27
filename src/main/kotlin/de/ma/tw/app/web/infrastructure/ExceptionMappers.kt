package de.ma.tw.app.web.infrastructure

import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class ExceptionMappers {

    @ServerExceptionMapper
    fun mapException(illegalArgumentException: IllegalArgumentException): RestResponse<String> {
        return RestResponse.status(RestResponse.Status.BAD_REQUEST, illegalArgumentException.message)
    }

}