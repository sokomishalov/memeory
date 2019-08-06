package ru.sokomishalov.memeory.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.http.codec.DecoderHttpMessageReader
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.ServerRequest.create
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import ru.sokomishalov.memeory.util.log.Loggable
import java.time.format.DateTimeParseException
import javax.naming.AuthenticationException
import javax.naming.NoPermissionException
import javax.naming.OperationNotSupportedException

@ControllerAdvice
class ExceptionHandlerController : Loggable {

    companion object {
        private val messageReaders = listOf<HttpMessageReader<*>>(DecoderHttpMessageReader(Jackson2JsonDecoder()))
    }

    @ExceptionHandler(IllegalArgumentException::class, NoSuchElementException::class, InvalidFormatException::class, DateTimeParseException::class, HttpMessageNotReadableException::class, MethodArgumentNotValidException::class, WebExchangeBindException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun badRequestException(e: Exception, exchange: ServerWebExchange): ResponseEntity<*> {
        val attributes = getErrorAttributes(exchange, BAD_REQUEST, e)
        return status(BAD_REQUEST).body(attributes)
    }

    @ExceptionHandler(AccessDeniedException::class, OperationNotSupportedException::class, NoPermissionException::class)
    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    fun forbiddenException(e: Exception, exchange: ServerWebExchange): ResponseEntity<*> {
        val attributes = getErrorAttributes(exchange, FORBIDDEN, e)
        return status(FORBIDDEN).body(attributes)
    }

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    fun unauthorizedException(e: Exception, exchange: ServerWebExchange): ResponseEntity<*> {
        val attributes = getErrorAttributes(exchange, UNAUTHORIZED, e)
        return status(UNAUTHORIZED).body(attributes)
    }


    @ExceptionHandler(UnsupportedOperationException::class)
    @ResponseStatus(NOT_IMPLEMENTED)
    @ResponseBody
    fun handleNotRealized(e: Exception, exchange: ServerWebExchange): ResponseEntity<*> {
        val attributes = getErrorAttributes(exchange, NOT_IMPLEMENTED, e)
        return status(NOT_IMPLEMENTED).body(attributes)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun internalServerError(e: Exception, exchange: ServerWebExchange): ResponseEntity<*> {
        logError(e)
        val attributes = getErrorAttributes(exchange, INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера")
        return status(INTERNAL_SERVER_ERROR).body(attributes)
    }

    private fun getErrorAttributes(exchange: ServerWebExchange, status: HttpStatus, e: Exception): Map<String, Any> {
        return getErrorAttributes(exchange, status, e.message)
    }

    private fun getErrorAttributes(exchange: ServerWebExchange, status: HttpStatus, message: String?): Map<String, Any> {
        val request = create(exchange, messageReaders)
        val defaultErrorAttributes = DefaultErrorAttributes()
        defaultErrorAttributes.storeErrorInformation(ResponseStatusException(status, message), exchange)
        return defaultErrorAttributes.getErrorAttributes(request, false)
    }
}
