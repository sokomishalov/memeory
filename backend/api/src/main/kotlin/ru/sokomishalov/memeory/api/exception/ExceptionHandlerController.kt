package ru.sokomishalov.memeory.api.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import ru.sokomishalov.commons.spring.exception.CustomExceptionHandler

@ControllerAdvice
class ExceptionHandlerController : CustomExceptionHandler()
