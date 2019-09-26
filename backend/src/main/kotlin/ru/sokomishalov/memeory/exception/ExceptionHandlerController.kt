package ru.sokomishalov.memeory.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import ru.sokomishalov.commons.spring.exception.CustomExceptionHandler

@ControllerAdvice
class ExceptionHandlerController : CustomExceptionHandler()
