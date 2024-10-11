package kr.goldenmine.disasterservicebackend.util

import kr.goldenmine.dowayobackend.util.impl.BadRequestException
import kr.goldenmine.dowayobackend.util.impl.ConflictException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.FileNotFoundException

@RestControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequestException(e: BadRequestException): Map<String, String> {
        val retMessages = HashMap<String, String>()
        retMessages["message"] = e.message
        return retMessages
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    fun handleConflictException(e: ConflictException): Map<String, String> {
        val retMessages = HashMap<String, String>()
        retMessages["message"] = e.message
        return retMessages
    }
}