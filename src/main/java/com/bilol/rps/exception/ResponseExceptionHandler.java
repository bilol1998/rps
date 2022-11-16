package com.bilol.rps.exception;

import com.bilol.rps.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Response<Object>> handleRuntimeException(Exception ex) {
        return new ResponseEntity<>(Response.errorResponse(ex, Response.Status.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Response<Object>> handleBadRequestException(Exception ex) {
        return new ResponseEntity<>(Response.errorResponse(ex, Response.Status.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

}
