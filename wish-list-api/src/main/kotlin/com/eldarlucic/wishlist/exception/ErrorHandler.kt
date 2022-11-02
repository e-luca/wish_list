package com.eldarlucic.wishlist.exception

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Error
import io.micronaut.http.exceptions.HttpStatusException

open class ErrorHandler {
    @Error
    fun exceptionHandler(exception: HttpStatusException): HttpResponse<*> {
        return HttpResponse.status<HttpStatusException>(exception.status, exception.message)
            .body("${exception.status}: ${exception.message}")
    }

    @Error
    fun internalExceptionHandler(exception: Exception): HttpResponse<*> {
        return HttpResponse.serverError<Exception>()
            .body(exception.message)
    }
}